package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Adds book
 */
public class AddBookActivity extends ActionBarActivity {
    public String  title;
    public String  author;
    public Integer imageID = R.mipmap.ic_generic_cover;        // For now, this is always the same

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Get reference to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the button that will allow the user to add a new book
        setupAddNewBookButton();

        // Set up a text change listener to watch the title EditText
        setupTextChangeListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
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
        Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Watch for when the user changes the text in the "Title" EditText field. If the user types
     * in a title that is already in their library, let them know about it.
     */
    public void setupTextChangeListener() {
        // The title field
        final EditText title = (EditText)findViewById(R.id.title);
        title.addTextChangedListener(new TextWatcher() {

            // We don't need to do anything in these methods
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // When the user types in a title, check to see if it is already in the library
            // If it is in the library, display the explanatory TextView
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Library.getInstance().contains(new Book(title.getText().toString(), null))) {
                    TextView textView = (TextView)findViewById(R.id.textView3);
                    textView.setText("\"" + title.getText().toString() + "\" is already in your library");
                    textView.setVisibility(View.VISIBLE);
                } else {
                    // Otherwise, keep it hidden
                    TextView textView = (TextView)findViewById(R.id.textView3);
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * Create the button that the user will press when they wish to add a new book to the library.
     *
     * When the button is clicked, the new book will be passed back to the main activity
     * ONLY if the information fields have not been left blank
     */
    void setupAddNewBookButton() {
        Button btn = (Button) findViewById(R.id.addBook);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title of the new book from the user
                EditText bookTitle = (EditText)findViewById(R.id.title);
                EditText bookAuthor = (EditText)findViewById(R.id.author);
                title = bookTitle.getText().toString();
                author = bookAuthor.getText().toString();

                // The new book will only be added to the library if the user filled out
                // all of the necessary fields (title and author)
                if (title != null && author != null) {
                    if (!title.isEmpty() && !author.isEmpty()) {
                        // Create the new book
                        Book newBook = new Book(title, author);
                        newBook.setImageId(imageID);

                        // Add the book and display a message
                        if (!Library.getInstance().contains(newBook)) {
                            Library.getInstance().addBook(newBook);
                            Log.i("DBManager", "Added Book: " + newBook.toString() + " with an ID of: " + newBook.getId());
                            Toast.makeText(AddBookActivity.this, "\"" + title + "\" has been added to your library", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("DBManager", "Database already contains book: " + newBook.getTitle());
                            return;
                        }

                        // Return to the main activity
                        Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
