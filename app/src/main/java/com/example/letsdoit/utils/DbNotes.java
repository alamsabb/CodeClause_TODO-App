package com.example.letsdoit.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.letsdoit.Modeal;

import java.util.ArrayList;
import java.util.List;

public class DbNotes extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DATABASE_NAME="TODO";
    private static final String TABLE_NAME="TODO_TABLE";
    private static final String COL1="ID";
    private static final String COL2="TASK";
    private static final String COL3="STATUS";




    public DbNotes(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT ,STATUS INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
          onCreate(db);

    }

    public void insert(Modeal modeal){
        database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL2,modeal.getTask());
        values.put(COL3,0);
        database.insert(TABLE_NAME,null,values);


    }
    public void update(int id,String task){
        database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL2,task);
        database.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});


    }
    public void updateStatus(int id,int status){
        database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL3,status);
        database.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
    }
    public void delete(int id){
        database=this.getWritableDatabase();
        database.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }
    @SuppressLint("Range")
    public List<Modeal> show(){
        database=this.getWritableDatabase();
        Cursor cursor=null;
        List<Modeal> list=new ArrayList<>();
        database.beginTransaction();
        try {
            cursor=database.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do{
                        Modeal task=new Modeal();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COL2)));
                        task.setDone(cursor.getInt(cursor.getColumnIndex(COL3)));
                        list.add(task);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            database.endTransaction();
            cursor.close();
        }
        return list;


    }
}
