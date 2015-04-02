package edu.byui.cs246.bookwarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    Book thisBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        // receive book through intent from BOOK DETAILS ACTIVITY class
        Book oldBook = (Book)getIntent().getSerializableExtra("thisBook");
        thisBook = Library.getInstance().getBook(oldBook.getId());

        // Get reference to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(thisBook.getTitle() + " - Notes");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the visibility of the TextView elements
        setVisibility();

        // display the list
        displayNoteObjects();

        // Register the listview for the context menu
        final ListView lv = (ListView)findViewById(R.id.listNote);
        registerForContextMenu(lv);

        //The list listener itself, in all its glory
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.showContextMenuForChild(view);
            }
        });

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

        // If the user presses the back button in the menu bar
        if (id==android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ON BACK PRESSED
     * Returns the user to Book Details Activity if Back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(ListNoteActivity.this, BookDetailsActivity.class);
        intent.putExtra("thisBook", Library.getInstance().getBook(thisBook.getId()));
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if (view.getId() == R.id.listNote) {
            menu.setHeaderTitle("Note Options");
            menu.add(Menu.NONE, 0, Menu.NONE, "Edit");
            menu.add(Menu.NONE, 1, Menu.NONE, "Remove");
        }
    }

    /**
     * Defines behavior for when the user selects a context menu item
     * Source referenced: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
     * @param item The context menu item that was selected
     * @return Return false to allow normal context menu processing to proceed, true to consume it here
     */
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        AdapterView<?> adapter = (AdapterView)findViewById(R.id.listNote);
        final Note note = (Note)adapter.getItemAtPosition(menuInfo.position);

        switch (itemId) {
            case 0:
                Intent intent = new Intent(ListNoteActivity.this, EditNoteActivity.class);
                intent.putExtra("thisNote", note);
                startActivity(intent);
                return true;
            case 1:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to remove this note?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Library.getInstance().removeNote(note);
                                setVisibility();
                                displayNoteObjects();
                                Toast.makeText(ListNoteActivity.this, "Note Removed", Toast.LENGTH_SHORT).show();                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG_LIST_NOTE_ACTVITY, "Note was not removed");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return true;
        }
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
                intent.putExtra("thisBook", Library.getInstance().getBook(thisBook.getId()));

                // run intent
                startActivity(intent);
            }
        });
    }

    /**
     * Convert our list of notes into an array
     * @return Returns an array of notes
     */
    public Note[] getNotesArray() {
        listNotes = Library.getInstance().getBook(thisBook.getId()).getNotes();
        Note[] noteArray = new Note[listNotes.size()];

        for (int i = 0; i < listNotes.size(); ++i) {
            noteArray[i] = listNotes.get(i);
        }
        return noteArray;
    }

    /**
     * DISPLAY NOTE OBJECTS
     * Delivers data from list to display
     */
    private void displayNoteObjects() {
        // create view
        ListView listView = (ListView) findViewById(R.id.listNote);

        // create adapter
        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getNotesArray());

        // set adapter to view
        listView.setAdapter(adapter);

        // log number of notes
        Log.i(TAG_LIST_NOTE_ACTVITY, "Loaded " + listNotes.size() + " notes.");
    }

    /**
     * Set the visibility of the TextView items
     */
    private void setVisibility() {
        // Get references to the TextView items
        TextView title = (TextView)findViewById(R.id.book_title);
        title.setText(thisBook.getTitle());
        TextView noNotes = (TextView)findViewById(R.id.no_notes);

        // Display them if there are no notes
        if (Library.getInstance().getBook(thisBook.getId()).getNotes().isEmpty()) {
            title.setVisibility(View.VISIBLE);
            noNotes.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.INVISIBLE);
            noNotes.setVisibility(View.INVISIBLE);
        }
    }
}
