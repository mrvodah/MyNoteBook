package com.example.vietvan.mynotebook.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vietvan.mynotebook.database.NoteBaseHelper;
import com.example.vietvan.mynotebook.database.NoteCursorWrapper;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.vietvan.mynotebook.database.NotedbSchema.NoteTable;

/**
 * Created by VietVan on 01/03/2018.
 */

public class NoteLab {
    private static NoteLab noteLab;
    private Context appContext;
    private SQLiteDatabase mDatabase;

    public NoteLab(Context appContext) {
        this.appContext = appContext;
        mDatabase = new NoteBaseHelper(appContext).getWritableDatabase();
    }

    public static NoteLab get(Context c){
        if(noteLab == null)
            noteLab = new NoteLab(c.getApplicationContext());
        return noteLab;
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = queryNotes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return notes;
    }

    public void addNote(Note note){
        ContentValues values = getContentValues(note);
        mDatabase.insert(NoteTable.NAME, null, values);
    }

    public void deleteNote(Note note){
        mDatabase.delete(
                NoteTable.NAME,
                NoteTable.Cols.UUID + " = ?",
                new String[] {note.getId().toString()}
                );
    }

    public void updateNote(Note note){
        String uuid = note.getId().toString();
        ContentValues values = getContentValues(note);

        mDatabase.update(
                NoteTable.NAME,
                values,
                NoteTable.Cols.UUID + " = ?",
                new String[] {uuid}
        );
    }

    public Note getNote(UUID id){
        NoteCursorWrapper cursor = queryNotes(
                NoteTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try {
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getNote();
        }
        finally {
            cursor.close();
        }
    }

    private ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        values.put(NoteTable.Cols.UUID, note.getId().toString());
        values.put(NoteTable.Cols.TITLE, note.getTitle());
        values.put(NoteTable.Cols.CONTENT, note.getContent());
        values.put(NoteTable.Cols.DATE, note.getDate().getTime());
        values.put(NoteTable.Cols.ALARM, note.isAlarm() ? 1 : 0);

        return values;
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] args){
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                args,
                null,
                null,
                null,
                null

        );
        return new NoteCursorWrapper(cursor);
    }

}
