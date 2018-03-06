package com.example.vietvan.mynotebook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.vietvan.mynotebook.base.Note;

import java.util.Date;
import java.util.UUID;

import static com.example.vietvan.mynotebook.database.NotedbSchema.NoteTable;

public class NoteCursorWrapper extends CursorWrapper {
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String uuid = getString(getColumnIndex(NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        String content = getString(getColumnIndex(NoteTable.Cols.CONTENT));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));
        int alarm = getInt(getColumnIndex(NoteTable.Cols.ALARM));

        Note note = new Note(UUID.fromString(uuid));
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date(date));
        note.setAlarm(alarm != 0);

        return note;
    }

}
