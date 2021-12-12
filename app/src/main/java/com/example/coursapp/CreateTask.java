package com.example.coursapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class CreateTask extends AppCompatActivity implements TimePickerFragment.TimePickerListener, DatePickerFragment.DatePickerListener {
    private ImageButton timeBtn;
    private ImageButton dateBtn;
    private ImageButton saveBtn;
    private TextView displayTime;
    private TextView displayDate;
    private TextView textTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        displayTime = findViewById(R.id.task_info_msg_time);
        displayDate = findViewById(R.id.task_info_msg_date);


        timeBtn = findViewById(R.id.choose_time);
        timeBtn.setOnClickListener(v -> onCreateTimeDialog(null));

        dateBtn = findViewById(R.id.choose_date);
        dateBtn.setOnClickListener(v -> onCreateDateDialog(null));

        textTask = findViewById(R.id.text_task);


        saveBtn = findViewById(R.id.save_task);
        saveBtn.setOnClickListener(v -> saveTask(null));

    }

    public void onCreateTimeDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onCreateDateDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        String min;
        String hr;
        if(minute < 10) {
           min = "0" + minute;
        } else {
           min = "" + minute;
        }

        if(hour < 10) {
            hr = "0" + hour;
        } else {
            hr = "" + hour;
        }

        String timeFull = getString(R.string.time,hr + ":" + min);

        displayTime.setText(timeFull);
        displayTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDateSet(DatePicker date, int year, int month, int dayOfMonth) {

        if(month + 1 >= 10) {
            displayDate.setText(year + "-" + (month + 1) + "-" + "-" + dayOfMonth);
        } else {
            displayDate.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
        }


        String dateFull = getString(R.string.date,displayDate.getText().toString());
        displayDate.setText(dateFull);
        displayDate.setVisibility(View.VISIBLE);
    }


    public void saveTask(View view) {

        MyDatabaseHelper myDB = new MyDatabaseHelper(CreateTask.this);
        myDB.addTask(textTask.getText().toString(),displayDate.getText().toString(),displayTime.getText().toString());

        Intent intent = new Intent(CreateTask.this, MainActivity.class);
        startActivity(intent);
    }





}