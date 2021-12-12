package com.example.coursapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public interface TimePickerListener {
        void onTimeSet(TimePicker timePicker, int hour, int minute);
    }

    TimePickerListener mListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (TimePickerListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implements TimePickerListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mListener.onTimeSet(timePicker,hourOfDay,minute);
    }


}

/*
this class for change design to TimeView
package com.example.coursapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get a Calendar instance
        final Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // TimePickerDialog Theme : THEME_DEVICE_DEFAULT_LIGHT
        TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

        // TimePickerDialog Theme : THEME_DEVICE_DEFAULT_DARK
        TimePickerDialog tpd2 = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, hour, minute, false);

        // TimePickerDialog Theme : THEME_HOLO_DARK
        TimePickerDialog tpd3 = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_DARK, this, hour, minute, false);

        // TimePickerDialog Theme : THEME_HOLO_LIGHT
        TimePickerDialog tpd4 = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, false);

        // TimePickerDialog Theme : THEME_TRADITIONAL
        TimePickerDialog tpd5 = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_TRADITIONAL, this, hour, minute, false);

        // Return the TimePickerDialog
        return tpd;      //return your themed timepicker like tpd2, tpd3 etc..
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
 */