package com.example.vietvan.mynotebook.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.vietvan.mynotebook.StaticVirtual;
import com.example.vietvan.mynotebook.base.Note;
import com.example.vietvan.mynotebook.fragment.NoteFragment;
import com.example.vietvan.mynotebook.base.NoteLab;
import com.example.vietvan.mynotebook.fragment.NoteListFragment;
import com.example.vietvan.mynotebook.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by VietVan on 01/03/2018.
 */

public class NotePagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<Note> notes;
    UUID noteid;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        notes = (NoteLab.get(this)).getNotes();

        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return NoteFragment.newInstance(notes.get(position).getId());
            }

            @Override
            public int getCount() {
                return notes.size();
            }
        });

        noteid = (UUID)getIntent().getSerializableExtra(NoteFragment.EXTRA_ID);
        for(int i=0;i<notes.size();i++)
            if(notes.get(i).getId().equals(noteid)){
                viewPager.setCurrentItem(i);
                break;
            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(StaticVirtual.isfab){
            NoteLab.get(this).deleteNote(NoteLab.get(this).getNote(noteid));
            StaticVirtual.isfab = false;
        }
    }
}
