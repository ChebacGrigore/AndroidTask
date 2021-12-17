package com.example.coursapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private Button nty;
    private RecyclerView recyclerView;


    MyDatabaseHelper myDB;

    ArrayList<String> task_id, task_date, task_time, task_text;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name  = getString(R.string.channel_id);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.channel_id),
                    name,importance);
            notificationChannel.setDescription("test_set_description");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        button = findViewById(R.id.create_task);
        button.setOnClickListener(v -> createTask());

        nty = findViewById(R.id.notify);
        nty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNotification();
            }
        });

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

    public void testNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,
                getString(R.string.channel_id))
                .setSmallIcon(R.drawable.left_btn)
                .setContentTitle("Prikolno")
                .setContentText("hz iiia proverim")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(123, builder.build());
    }

    public void createNotificationChannel(){


    }

    public void showTestNotification() {


    }


}

