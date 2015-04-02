package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditNoteActivity extends ActionBarActivity {
    private Note     thisNote;
    private EditText content;
    private EditText pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Get reference to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Run function
        run();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
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
        finish();
    }

    /**
     * Start by getting the references we need
     */
    private void run() {
        // First off, get the note from the previous activity
        Note previous = (Note)getIntent().getSerializableExtra("thisNote");
        thisNote = Library.getInstance().getNote(previous.getId());

        // Get the reference to the content field
        content = (EditText)findViewById(R.id.content);
        content.setText(thisNote.getNoteContent());
        content.requestFocus();

        // Get the reference to the page number field
        pageNumber = (EditText)findViewById(R.id.page);
        if (thisNote.getPageNumber() != 0) {
            pageNumber.setText(String.valueOf(thisNote.getPageNumber()));
        }
    }

    /**
     * Save any changes that the user made to the note
     */
    public void save(View view) {
        // Set the content of the note
        thisNote.setNoteContent(content.getText().toString());

        // If the user entered a valid page number, set the page number of the note
        if (!pageNumber.getText().toString().isEmpty() && isInteger(pageNumber.getText().toString())) {
            thisNote.setPageNumber(Integer.parseInt(pageNumber.getText().toString()));
        } else if (pageNumber.getText().toString().isEmpty()) {
            thisNote.setPageNumber(0);
        } else {
            Log.e("EditNoteActivity", "The user entered an invalid page number.");
            thisNote.setPageNumber(0);
        }

        // Update the note in our database
        Library.getInstance().updateNote(thisNote);

        // Return to the list note activity
        Intent intent = new Intent(EditNoteActivity.this, ListNoteActivity.class);
        intent.putExtra("thisBook", Library.getInstance().getBook(thisNote.getBookId()));
        startActivity(intent);
        finish();
    }

    /**
     * Determine if a string is a valid integer
     * References: http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
     * @param str The string to check
     * @return Returns true if the string is a valid integer
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
