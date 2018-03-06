package com.example.vietvan.mynotebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vietvan.mynotebook.database.NotedbSchema;
import com.example.vietvan.mynotebook.database.NotedbSchema.NoteTable;

/**
 * Created by VietVan on 03/03/2018.
 */

public class NoteBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "noteBase.db";
    public static final int VERSION = 1;

    public NoteBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + NoteTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        NoteTable.Cols.UUID + ", " +
                        NoteTable.Cols.TITLE + ", " +
                        NoteTable.Cols.CONTENT + ", " +
                        NoteTable.Cols.DATE + ", " +
                        NoteTable.Cols.ALARM +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
