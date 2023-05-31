package com.example.letsdoit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdoit.utils.DbNotes;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    List<Modeal> list;
    MainActivity mainActivity;
    DbNotes database;

    public Adapter(MainActivity mainActivity, DbNotes database) {
        this.mainActivity = mainActivity;
        this.database = database;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Modeal todo=list.get(position);
        holder.checkBox.setText(todo.getTask());
        holder.checkBox.setChecked(convert(todo.getDone()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    database.updateStatus(todo.getId(),1);
                }else
                    database.updateStatus(todo.getId(),0);
            }
        });

    }
    public Context getContext(){
        return mainActivity;
    }
    public void setTask(List<Modeal> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public void deletetask(int position){
        Modeal modeal=list.get(position);
        database.delete(modeal.getId());
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void editTask(int position){
        Modeal item=list.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        add_newtask add=new add_newtask();
        add.setArguments(bundle);
        add.show(mainActivity.getSupportFragmentManager(),add.getTag());

    }
    public boolean convert(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);

        }
    }






}
