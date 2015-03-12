package com.example.mac.sql_prac;

/**
 * Created by Mac on 3/11/2015.
 */
public class Note {
    private int mID;
    private String mDate;
    private String mNote;

    public Note(){

    }

    public Note(int id, String date, String note){
        mID = id;
        mDate = date;
        mNote = note;
    }

    public Note(String date, String note){
        mDate = date;
        mNote = note;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

}
