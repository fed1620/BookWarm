package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddBook extends ActionBarActivity {

    public String title;
    public Integer imageID = R.mipmap.ic_generic_cover;        // For now, this is always the same

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Set up the button that will allow the user to add a new book
        setupAddNewBookButton();
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

        return super.onOptionsItemSelected(item);
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
                title = bookTitle.getText().toString();

                // The new book will only be added to the library if the user filled out
                // all of the necessary fields (For the time being, just the title)
                if (title != null) {
                    if (!title.isEmpty()) {
                        Book newBook = new Book();
                        newBook.setTitle(title);
                        newBook.setImageId(imageID);

                        Library library = (Library)getIntent().getSerializableExtra("library");
                        library.addBook(newBook);

                        // Create a new intent that we will use to pass the book
                        // back to the main activity
                        Intent intentPassBook = new Intent(AddBook.this, MainActivity.class);
                        startActivity(intentPassBook);
                    }
                }
            }
        });
    }
}
