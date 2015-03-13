package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * LIST NOTE ACTIVITY
 * Implements list of notes page
 */
public class ListNoteActivity extends ActionBarActivity {
    //Constants:
    private static final String TAG_LIST_NOTE_ACTVITY = "ListNoteActivity";

    //Variables
    List<Note> listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        // receive book through intent from BOOK DETAILS ACTIVITY class
        Book thisBook = (Book)getIntent().getSerializableExtra("thisBook");
        listNotes = thisBook.getNotes();

        // display the list
        displayNotes();

        // setup button
        setupAddNoteButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_note, menu);
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

    /**
     * SETUP ADD NOTE BUTTON
     * Creates ADD NOTE ACTIVITY class by intent
     **/
    void setupAddNoteButton() {
        Button btn = (Button) findViewById(R.id.addNote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent
                Intent intent = new Intent(ListNoteActivity.this, AddNoteActivity.class);
                // run intent
                startActivity(intent);
            }
        });
    }

    /**
     * DISPLAY NOTES
     * Delivers data from list to display
     */
    private void displayNotes() {
        // create view
        ListView listView = (ListView) findViewById(R.id.listView);

        // create adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNotes);

        // set adapter to view
        listView.setAdapter(adapter);

        // log number of notes
        Log.i(TAG_LIST_NOTE_ACTVITY, "Loaded " + listNotes.size() + " notes.");
    }
}
