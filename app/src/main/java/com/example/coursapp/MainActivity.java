package com.example.coursapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private RecyclerView recyclerView;


    MyDatabaseHelper myDB;

    ArrayList<String> task_id, task_date, task_time, task_text;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.create_task);
        button.setOnClickListener(v -> createTask());


        recyclerView = findViewById(R.id.recycle_view);
        myDB = new MyDatabaseHelper(MainActivity.this);

        task_id = new ArrayList<>();
        task_date = new ArrayList<>();
        task_time = new ArrayList<>();
        task_text = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, task_id, task_date, task_time,
                task_text);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    public void createTask(){

        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }

    void storeDataInArrays() {

        Cursor cursor = myDB.readAllData();

        if(cursor.getCount() == 0) {

            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                task_id.add(cursor.getString(0));
                task_date.add(cursor.getString(2));
                task_time.add(cursor.getString(3));
                task_text.add(cursor.getString(1));
            }
        }
    }

}

