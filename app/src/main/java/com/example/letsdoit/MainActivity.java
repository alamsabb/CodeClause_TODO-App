package com.example.letsdoit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.letsdoit.utils.DbNotes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onclose {
    RecyclerView recyclerView;
    List<Modeal> list;
    Adapter adapter;
    FloatingActionButton floatingActionButton;
    DbNotes dbNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        dbNotes=new DbNotes(MainActivity.this);
        list=new ArrayList<>();
        adapter=new Adapter(MainActivity.this,dbNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        list=dbNotes.show();
        Collections.reverse(list);
        adapter.setTask(list);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_newtask.add().show(getSupportFragmentManager(),add_newtask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new touch(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onclose(DialogInterface dialogInterface) {
        list=dbNotes.show();
        Collections.reverse(list);
        adapter.setTask(list);
        adapter.notifyDataSetChanged();
    }
}