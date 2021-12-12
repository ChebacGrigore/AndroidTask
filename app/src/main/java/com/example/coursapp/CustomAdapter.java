package com.example.coursapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractList;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private  Context context;
    private  ArrayList task_id, task_date, task_time, purpose_text;
    Activity activity;
    int position;

    CustomAdapter (Activity activity,Context context, ArrayList task_id, ArrayList task_date, ArrayList task_time,
                   ArrayList purpose_text) {

        this.activity  = activity;
        this.context   = context;
        this.task_id   = task_id;
        this.task_date = task_date;
        this.task_time = task_time;
        this.purpose_text = purpose_text;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        this.position = position;

        holder.task_id_txt.setText(String.valueOf(task_id.get(position)));
        holder.task_date_txt.setText(String.valueOf(task_date.get(position)));
        holder.task_time_txt.setText(String.valueOf(task_time.get(position)));
        holder.task_purpose_txt.setText(String.valueOf(purpose_text.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(task_id.get(position)));
                intent.putExtra("date", String.valueOf(task_date.get(position)));
                intent.putExtra("time", String.valueOf(task_time.get(position)));
                intent.putExtra("purpose", String.valueOf(purpose_text.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public int getItemCount() {

        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task_id_txt, task_date_txt, task_time_txt, task_purpose_txt;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt   = itemView.findViewById(R.id.task_id_adp);
            task_date_txt = itemView.findViewById(R.id.task_date_adp);
            task_time_txt = itemView.findViewById(R.id.task_time_adp);
            task_purpose_txt   = itemView.findViewById(R.id.purpose_text_adp);
            mainLayout    = itemView.findViewById(R.id.mainLayout);
        }
    }
}
