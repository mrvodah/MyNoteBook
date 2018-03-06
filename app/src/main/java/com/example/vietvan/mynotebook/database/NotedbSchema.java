package com.example.vietvan.mynotebook.database;

/**
 * Created by VietVan on 03/03/2018.
 */

public class NotedbSchema {
    public static final class NoteTable{
        public static final String NAME = "note";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String CONTENT = "content";
            public static final String DATE = "date";
            public static final String ALARM = "alarm";
        }
    }
}
