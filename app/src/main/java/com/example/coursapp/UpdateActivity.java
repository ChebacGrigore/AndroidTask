package com.example.coursapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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


    ImageView  update_task, test_delete;
    TextView choose_date, choose_time, show_purpose;

    String id, date, time, purpose;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_update);

        choose_date = findViewById(R.id.task_info_msg_date2);
        choose_time = findViewById(R.id.task_info_msg_time2);
        update_task = findViewById(R.id.save_task2);
        test_delete = findViewById(R.id.test_delete);



        show_purpose = findViewById(R.id.text_task2);


        getAndSetIntentDate();

        test_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOneRow(id);
                finish();
            }
        });

        choose_time.setOnClickListener(this::onCreateTimeDialog);
        choose_date.setOnClickListener(this::onCreateDateDialog);

        update_task.setOnClickListener(v -> {

            purpose = show_purpose.getText().toString();
            date = choose_date.getText().toString();
            time = choose_time.getText().toString();
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
            choose_date.setText(date);
            choose_time.setText(time);
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
            choose_date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        } else {
            choose_date.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
        }


        String dateFull = getString(R.string.date,choose_date.getText().toString());
        choose_date.setText(dateFull);
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
        choose_time.setText(timeFull);
    }

    public void deleteOneRow(String row_id) {

        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
        myDB.deleteOnRow(row_id);
    }
}
