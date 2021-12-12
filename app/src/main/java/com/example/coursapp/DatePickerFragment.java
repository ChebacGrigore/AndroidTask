package com.example.coursapp;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    public interface DatePickerListener {
        void onDateSet(DatePicker date, int year, int month, int dayOfMonth);
    }

    DatePickerFragment.DatePickerListener mListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DatePickerFragment.DatePickerListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implements DatePickerListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog chooseDate = new DatePickerDialog(getActivity(), this,year,month,day);

        return chooseDate;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mListener.onDateSet(datePicker,year,month,dayOfMonth);
    }
}
