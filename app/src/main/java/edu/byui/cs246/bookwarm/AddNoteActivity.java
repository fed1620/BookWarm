package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Adds Note
 */
public class AddNoteActivity extends ActionBarActivity {
    private static final String TAG_ADD_NOTE_ACTIVITY = "AddNoteActivity";

    Book thisBook;
    Note newNote;
    TextView pageNumber;
    TextView noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        thisBook = (Book) getIntent().getSerializableExtra("thisBook");

        // Get reference to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Give focus to the text box
        noteContent = (TextView) findViewById(R.id.editText);
        noteContent.requestFocus();

        setupCreateNoteButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
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

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNoteActivity.this, ListNoteActivity.class);
        intent.putExtra("thisBook", Library.getInstance().getBook(thisBook.getId()));
        startActivity(intent);
        finish();
    }

    /**
     * Self-explanatory
     */
    private void setupCreateNoteButton() {
        Button createNoteButton = (Button) findViewById(R.id.createNote);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber  = (TextView) findViewById(R.id.pageNumber);
                noteContent = (TextView) findViewById(R.id.editText);

                if (noteContent.getText().toString().isEmpty()) {
                    return;
                }

                newNote = new Note();
                newNote.setPageNumber(buildPageNumber());
                newNote.setNoteContent(noteContent.getText().toString());

                thisBook.addNote(newNote);
                Library.getInstance().updateBook(thisBook);

                Intent intent = new Intent(AddNoteActivity.this, ListNoteActivity.class);
                intent.putExtra("thisBook", thisBook);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Build the page number for the Note.
     * @return returns the page number for the note entered. If none was entered, it is 0.
     */
    private int buildPageNumber() {
        if (!pageNumber.getText().toString().isEmpty()) {
            String pageNumberContent = pageNumber.getText().toString();
            Log.i(TAG_ADD_NOTE_ACTIVITY, pageNumberContent);
            if (isInteger(pageNumberContent)) {
                return Integer.parseInt(pageNumberContent);
            } else {
                Log.e(TAG_ADD_NOTE_ACTIVITY, "The user entered an invalid page number.");
                //TODO: Implement a 'try again' feature. For now, return 0.
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Checks to see if a string is an integer as efficiently as possible.
     * References: http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
     * @param str The string to be check.
     * @return A bool whether or not the string is an integer.
     */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }
}
