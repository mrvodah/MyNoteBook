package com.example.vietvan.mynotebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vietvan.mynotebook.StaticVirtual;
import com.example.vietvan.mynotebook.base.Note;
import com.example.vietvan.mynotebook.fragment.NoteFragment;
import com.example.vietvan.mynotebook.base.NoteLab;
import com.example.vietvan.mynotebook.R;
import com.github.clans.fab.FloatingActionButton;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public static FloatingActionButton fab;

    public abstract Fragment createFragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();

        Fragment frag = createFragment();
        fm.beginTransaction()
                .replace(R.id.frame, frag)
                .commit();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticVirtual.isfab = true;
                Note n = new Note();
                NoteLab.get(SingleFragmentActivity.this).addNote(n);
                Intent i = new Intent(SingleFragmentActivity.this, NotePagerActivity.class);
                i.putExtra(NoteFragment.EXTRA_ID, n.getId());
                startActivity(i);
            }
        });
    }
}
