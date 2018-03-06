package com.example.vietvan.mynotebook.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.vietvan.mynotebook.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "DATE";
    Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_date_datepicker, null);

        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(mDate);

        DatePicker date = v.findViewById(R.id.datepicker);
        date.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE)).getTime();

                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel,  null)
                .create();
    }

    public void sendResult(int resultCode){
        if(getTargetFragment() == null) return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment d = new DatePickerFragment();
        d.setArguments(args);

        return d;
    }

}
