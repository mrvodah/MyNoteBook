package com.example.vietvan.mynotebook.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by VietVan on 01/03/2018.
 */

public class Note {
    UUID id;
    String title, content;
    Date date;
    boolean isAlarm;

    public Note() {
        this(UUID.randomUUID());
    }

    public Note(UUID id) {
        this.id = id;
        title = "";
        content = "";
        isAlarm = false;
        date = new Date();
    }

    public Note(String title, String content) {
        id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        isAlarm = false;
        date = new Date();
    }

    public Note(String title, String content, boolean isAlarm, Date date) {
        this.title = title;
        this.content = content;
        this.isAlarm = isAlarm;
        this.date = date;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatDate(){
        SimpleDateFormat simple = new SimpleDateFormat("EEEE, MMM dd yyyy");
        return simple.format(date);
    }

    public String getFormatTime(){
        SimpleDateFormat simple = new SimpleDateFormat("EEEE, MMM dd yyy, HH:mm");
        return simple.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }
}
