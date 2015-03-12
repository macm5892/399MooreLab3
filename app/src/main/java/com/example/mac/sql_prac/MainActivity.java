package com.example.mac.sql_prac;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //handled to GUI components
    TextView mNoteIDView;
    EditText mDateTextView;
    EditText mNoteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteIDView = (TextView) findViewById(R.id.noteIdValue);
        mDateTextView = (EditText) findViewById(R.id.dateEditText);
        mNoteTextView = (EditText) findViewById(R.id.noteEditText);


    }

    public void onAddClick(View view){
        //create the DB handler
        NotesDBHandler dbHandler = new NotesDBHandler(this, null, null, 1);

        //get the user indicated date
        String myDate = mDateTextView.getText().toString();

        //get the user indicated note
        String myNotes = mNoteTextView.getText().toString();

        //make a new note with these values
        Note myNote = new Note(myDate, myNotes);

        //Add the note to the DB
        dbHandler.addNote(myNote);

        //clear the input fields
        mDateTextView.setText("");
        mNoteTextView.setText("");
    }

    public void onFindClick(View view){
        //create the DB handler
        NotesDBHandler dbHandler = new NotesDBHandler(this, null, null, 1);

        //get the user indicated date
        String myDate = mDateTextView.getText().toString();

        //see if that note exists with that date
        Note myNote = dbHandler.findNote(myDate);

        if(myNote != null){
            //set the ID and Note to be the one for the returned date
            mNoteIDView.setText(String.valueOf(myNote.getID()));
            mNoteTextView.setText(String.valueOf(myNote.getNote()));
        }

       else
           mNoteIDView.setText("No Match Found");
    }

    public void onDeleteClick(View view){
        //create the DB handler
        NotesDBHandler dbHandler = new NotesDBHandler(this, null, null, 1);

        //get the user indicated date
        String myDate = mDateTextView.getText().toString();

        //delete the record
        boolean myResult = dbHandler.deleteNote(myDate);

        //if deleted successfully
        if(myResult){
            mNoteIDView.setText("Record Deleted");
            mDateTextView.setText("");
            mNoteTextView.setText("");
        }
        else
            mNoteIDView.setText("No Match Found");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
