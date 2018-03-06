package com.example.vietvan.mynotebook.activities;

import android.support.v4.app.Fragment;

import com.example.vietvan.mynotebook.fragment.NoteListFragment;

/**
 * Created by VietVan on 01/03/2018.
 */

public class NoteListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new NoteListFragment();
    }
}
