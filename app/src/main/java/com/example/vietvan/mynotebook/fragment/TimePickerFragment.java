package com.example.vietvan.mynotebook.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import com.example.vietvan.mynotebook.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "TIME";
    public static boolean isTime = false;
    Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_time_picker, null);

        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.setTime(mDate);

        TimePicker timer = v.findViewById(R.id.timepicker);
        timer.setCurrentHour(hour);
        timer.setCurrentMinute(minute);
        timer.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                mDate = new GregorianCalendar( calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), hour, minute).getTime();

                getArguments().putSerializable(EXTRA_TIME, mDate);
            }
        });

        TimePicker time = v.findViewById(R.id.timepicker);
        time.setIs24HourView(true);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    public void sendResult(int resultCode){
        if(getTargetFragment() == null) return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);

        isTime = true;
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment timer = new TimePickerFragment();
        timer.setArguments(args);

        return timer;
    }

}
