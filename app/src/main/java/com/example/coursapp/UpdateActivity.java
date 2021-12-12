package com.example.coursapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class UpdateActivity  extends AppCompatActivity implements TimePickerFragment.TimePickerListener,
        DatePickerFragment.DatePickerListener {


    ImageView choose_date, choose_time, update_task;
    TextView show_date, show_time, show_purpose;

    String id, date, time, purpose;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_update);

        choose_date = findViewById(R.id.choose_date2);
        choose_time = findViewById(R.id.choose_time2);
        update_task = findViewById(R.id.save_task2);

        show_date   = findViewById(R.id.task_info_msg_date2);
        show_time   = findViewById(R.id.task_info_msg_time2);
        show_purpose = findViewById(R.id.text_task2);


        getAndSetIntentDate();

        choose_time.setOnClickListener(this::onCreateTimeDialog);
        choose_date.setOnClickListener(this::onCreateDateDialog);
        update_task.setOnClickListener(v -> {

            purpose = show_purpose.getText().toString();
            date = show_date.getText().toString();
            time = show_time.getText().toString();
            MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
            myDb.updateRow(id,purpose,date,time);
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    void getAndSetIntentDate() {

        if (getIntent().hasExtra("id") && getIntent().hasExtra("date")
                && getIntent().hasExtra("time") && getIntent().hasExtra("purpose")) {

            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            purpose = getIntent().getStringExtra("purpose");

            //Setting data
            show_date.setText(date);
            show_time.setText(time);
            show_purpose.setText(purpose);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateTimeDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        newFragment.show(getSupportFragmentManager(), "timePickerUpd");
    }

    public void onCreateDateDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePickerUpd");
    }

    @Override
    public void onDateSet(DatePicker date, int year, int month, int dayOfMonth) {

        if(month + 1 >= 10) {
            show_date.setText(year + "-" + (month + 1) + "-" + "-" + dayOfMonth);
        } else {
            show_date.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
        }


        String dateFull = getString(R.string.date,show_date.getText().toString());
        show_date.setText(dateFull);
        show_date.setVisibility(View.VISIBLE);
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
        show_time.setText(timeFull);
        show_time.setVisibility(View.VISIBLE);
    }
}
