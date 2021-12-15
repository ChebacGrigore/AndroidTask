package com.example.coursapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TaskLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_tasks";
    private static final String COLUMN_ID  = "_id";
    private static final String COLUMN_TITLE  = "task_title";
    private static final String COLUMN_DATE   = "task_date";
    private static final String COLUMN_TIME   = "time";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                 "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                               COLUMN_TITLE + " TEXT, " +
                               COLUMN_DATE  + " TEXT, " +
                               COLUMN_TIME  + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTask(String title, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1) {
            Toast toast = Toast.makeText(context, "Failed",Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Added successfully",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void updateRow(String row_id, String title, String date, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "Unsuccessfully",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully",Toast.LENGTH_LONG).show();
        }
    }

    void deleteOnRow(String row_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, "_id=?",new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Unsuccessfully",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}




