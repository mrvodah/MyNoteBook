package com.example.vietvan.mynotebook.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vietvan.mynotebook.StaticVirtual;
import com.example.vietvan.mynotebook.base.Note;
import com.example.vietvan.mynotebook.base.NoteLab;
import com.example.vietvan.mynotebook.R;
import com.example.vietvan.mynotebook.activities.NotePagerActivity;

import java.util.ArrayList;


public class NoteListFragment extends ListFragment {

    ArrayList<Note> notes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Add values from SQL to notes
        notes = NoteLab.get(getActivity()).getNotes();

        // Set values on ListFragment
        setListAdapter(new NoteAdapter(notes));

    }

    // Register ContextMenu for ListFragment
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView(); //EX:
        listView.setTextFilterEnabled(true);
        listView.setDivider(Drawable.createFromPath("@null"));
        registerForContextMenu(listView);
    }

    @Override
    public void onResume() {
        super.onResume();
        //if(NoteFragment.isSave){
        // Reload List
            notes = NoteLab.get(getActivity()).getNotes();
            setListAdapter(new NoteAdapter(notes));
            ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
        //}
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        StaticVirtual.isView = true;
        Note n = ((NoteAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), NotePagerActivity.class);
        i.putExtra(NoteFragment.EXTRA_ID, n.getId());
        startActivity(i);
    }

    class NoteAdapter extends ArrayAdapter<Note> {

        public NoteAdapter(ArrayList<Note> smnotes) {
            super(getActivity(), 0, smnotes);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list, null);

            Note c =  getItem(position);

            TextView title = convertView.findViewById(R.id.textView_title);
            TextView content = convertView.findViewById(R.id.textView_content);
            TextView date = convertView.findViewById(R.id.textView_date);
            CheckBox isAlarm = convertView.findViewById(R.id.checkBox_alarm);

            title.setText(c.getTitle());
            content.setText(c.getContent());
            date.setText(c.getFormatTime());
            isAlarm.setChecked(c.isAlarm());

            return convertView;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action !");

        menu.add(0, 111, 0, "View Note");
        menu.add(0,222,1,"Edit Note");
        menu.add(0,333,2,"Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Note selectedNote = (Note)getListAdapter().getItem(info.position);

        switch (item.getItemId()){
            case 111: {
                Intent i = new Intent(getActivity(), NotePagerActivity.class);
                i.putExtra(NoteFragment.EXTRA_ID, selectedNote.getId());
                startActivity(i);
                break;
            }
            case 222: {
                Intent i = new Intent(getActivity(), NotePagerActivity.class);
                i.putExtra(NoteFragment.EXTRA_ID, selectedNote.getId());
                startActivity(i);
                break;
            }
            case 333: {
                new AlertDialog.Builder(getActivity())
                        .setMessage(selectedNote.getTitle() + " \nAre you sure to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NoteLab.get(getActivity()).deleteNote(selectedNote);
                                onResume();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                break;
            }
            default:
                return false;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
