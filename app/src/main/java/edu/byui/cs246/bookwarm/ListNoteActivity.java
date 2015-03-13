package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        // receive book through intent from BOOK DETAILS ACTIVITY class
        Book thisBook = (Book)getIntent().getSerializableExtra("thisBook");
        // display the list
        displayList(thisBook);

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
     * CREATE LIST
     * Delivers data from list to display
     * @param thisBook
     */
    void displayList(Book thisBook) {
        //testing...
        Book test = new Book();
        Note note = new Note();
        note.setNoteContent("test");
        note.setPageNumber(1);
        note.setId(11);
        test.addNote(note);
        test.addNote(note);
        test.addNote(note);
        test.addNote(note);

        // create list
        ListView listView = (ListView) findViewById(R.id.listNote);
        // create adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test.getNotes());
        // send adapter to display
        listView.setAdapter(arrayAdapter);
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
}
