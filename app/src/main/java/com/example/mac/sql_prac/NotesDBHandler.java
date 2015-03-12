package com.example.mac.sql_prac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mac on 3/11/2015.
 */
public class NotesDBHandler extends SQLiteOpenHelper {
    //declare DATABASE constants so only have one place to change them as updates are made
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "thomasmoore2.db";

    //declare TABLE constants for single place to make edits
    private static final String TABLE_DNOTES = "D_Notes";
    public static final String COLUMN_NID = "note_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NOTE = "note";

    //constructor
    public NotesDBHandler(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // override OnCreate
    @Override
    public void onCreate(SQLiteDatabase db){
        //construct the SQL command, using the constants declared above
        String CREATE_NOTES_TABLE = "CREATE TABLE " +
                TABLE_DNOTES + "(" +
                COLUMN_NID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_NOTE + " TEXT)";

        //Execute SQL command. No return value
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DNOTES);

        //recreate the table by calling onCreate
        onCreate(db);
    }

    //add a student to the D_Notes table in the DB
    public void addNote(Note note){
        //prepare the vales for the new entry
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, note.getDate());
        values.put(COLUMN_NOTE, note.getNote());

        //open DB
        SQLiteDatabase db = this.getWritableDatabase();

        //insert into the DB
        db.insert(TABLE_DNOTES, null, values);

        //close the DB
        db.close();
    }

    public Note findNote(String finddate){
        String sql_query = "SELECT * FROM " + TABLE_DNOTES +
                " WHERE " + COLUMN_DATE + " = \"" +
                finddate + "\"";

        //open the DB
        SQLiteDatabase db = this.getWritableDatabase();

        //execute the query on the database and store the returned rows
        Cursor myCursor = db.rawQuery(sql_query, null);

        //create an empty date object
        Note myNote = new Note();

        //if at least one row is returned, point to the first entry and extract the data

        if(myCursor.moveToFirst()){
            myNote.setID(myCursor.getInt(0));
            myNote.setDate(myCursor.getString(1));
            myNote.setNote(myCursor.getString(2));
            myCursor.close();
        }
        else
            //nothing was returned
            myNote = null;

        //close the DB
        db.close();
        return myNote;

    }

    public boolean deleteNote(String finddate){
        //set default return value
        boolean result = false;

        //construct the SQL string: SELECT * FROM <table> WHERE <col> = "<date>"
        String sql_query = "SELECT * FROM " + TABLE_DNOTES +
                " WHERE " + COLUMN_DATE + " = \"" +
                finddate + "\"";

        //open the DB
        SQLiteDatabase db = this.getWritableDatabase();

        //Execute the query on the database and store the returned rows
        Cursor myCursor = db.rawQuery(sql_query, null);

        //Create an empty Date object
        Note myNote = new Note();

        //if at least one row is returned, point to the first entry and extract the data
        if (myCursor.moveToFirst()) {
            //get the ID of the first return record
            myNote.setID(myCursor.getInt(0));

            //delete entry with NoteID set to first returned record
            db.delete(TABLE_DNOTES, COLUMN_NID + " = ?",
                    new String[]{String.valueOf(myNote.getID())});
            // close the cursor
            myCursor.close();
            //set the return value to true to indicate successful delete
            result = true;

        }

        //close the DB
        db.close();
        return result;

    }

}

