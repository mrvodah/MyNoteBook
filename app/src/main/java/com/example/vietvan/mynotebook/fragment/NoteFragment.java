package com.example.vietvan.mynotebook.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.vietvan.mynotebook.StaticVirtual;
import com.example.vietvan.mynotebook.base.Note;
import com.example.vietvan.mynotebook.base.NoteLab;
import com.example.vietvan.mynotebook.R;
import com.example.vietvan.mynotebook.activities.SingleFragmentActivity;

import java.util.Date;
import java.util.UUID;

public class NoteFragment extends Fragment implements View.OnClickListener {

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_TIME = 0;
    Note note;
    EditText title, content;
    EditText date;
    Button save, cancel;
    CheckBox alarm;
    Switch sw;
    public static String EXTRA_ID = "ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);

        setupUI(v);

        title.setText(note.getTitle());
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        content.setText(note.getContent());
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        updateDate();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(sw.getText() == "Time"){
                    TimePickerFragment dialog = TimePickerFragment.newInstance(note.getDate());
                    dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                    dialog.show(fm, DIALOG_TIME);
                }
                else{
                    DatePickerFragment dialog = DatePickerFragment.newInstance(note.getDate());
                    dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                    dialog.show(fm, DIALOG_DATE);
                }

            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sw.setText("Time");
                }
                else{
                    sw.setText("Date");
                }
            }
        });

        alarm.setChecked(note.isAlarm());
        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                note.setAlarm(b);
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            UUID id = (UUID) getArguments().getSerializable(EXTRA_ID);
            note = NoteLab.get(getActivity()).getNote(id);
        }
        catch(Exception ex){
            note = new Note();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        NoteLab.get(getActivity()).updateNote(note);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_DATE){
            if(TimePickerFragment.isTime){
                Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                note.setDate(date);
                updateDate();
                TimePickerFragment.isTime = false;
            }
            else{
                Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                note.setDate(date);
                updateDate();
            }

        }
        else if(requestCode == REQUEST_TIME){

        }
    }

    private void updateDate() {
        date.setText(note.getFormatTime());
    }

    public void setupUI(View v){
        title = v.findViewById(R.id.editText_title);
        content = v.findViewById(R.id.editText_content);
        date = v.findViewById(R.id.editText_date);
        save = v.findViewById(R.id.button_save);
        cancel = v.findViewById(R.id.button_cancel);
        sw = v.findViewById(R.id.spinner);
        alarm = v.findViewById(R.id.checkBox_alarm);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public static NoteFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ID, id);

        NoteFragment note = new NoteFragment();
        note.setArguments(args);

        return note;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_save: {
                Toast.makeText(getActivity(), "Save Completed!", Toast.LENGTH_SHORT).show();
                StaticVirtual.isfab = false;
                getActivity().onBackPressed();
                break;
            }
            case R.id.button_cancel: {
                Toast.makeText(getActivity(), "Cancel Save!", Toast.LENGTH_SHORT).show();
                if(StaticVirtual.isfab){
                    NoteLab.get(getActivity()).deleteNote(note);
                    StaticVirtual.isfab = false;
                }
                getActivity().onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                if(NavUtils.getParentActivityName(getActivity()) != null)
                    NavUtils.navigateUpFromSameTask(getActivity());
                if(StaticVirtual.isfab){
                    NoteLab.get(getActivity()).deleteNote(note);
                    StaticVirtual.isfab = false;
                }
                return true;
            }
            case R.id.note_delete: {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NoteLab.get(getActivity()).deleteNote(note);
                                StaticVirtual.isfab = false;
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
