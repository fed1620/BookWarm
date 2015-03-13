package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This activity will display all information about the book in detail, and will allow
 * the user to access the Notes relating to the book, set the read status, and rate the book
 */
public class BookDetailsActivity extends ActionBarActivity {
    private Book thisBook;
    private String bookDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //jumping to a run function because onCreate is cluttered as fudge
        run();

        setupListNoteButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
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

    @Override
    public void onBackPressed() {
        // When the user presses the back key, send the book (along with any changes made to it)
        // back to the main activity
        Intent intentPassBook = new Intent(BookDetailsActivity.this, MainActivity.class);
        intentPassBook.putExtra("updatedBook", thisBook);
        startActivity(intentPassBook);
        finish();
    }

    //The 'main' of this activity
    private void run() {
        if (getIntent().getSerializableExtra("thisBook") == null) {
            return;
        }
        thisBook = (Book)getIntent().getSerializableExtra("thisBook");
        setupDisplay();
        setupSpinner();
        setupRatingBarListener();
    }

    // Fill out the display information
    private void setupDisplay() {
        // Set the element variables
        ImageView coverIcon = (ImageView) findViewById(R.id.coverIcon);
        TextView  bookInfo  = (TextView)  findViewById(R.id.bookInfo);
        TextView  title     = (TextView)  findViewById(R.id.title);
        TextView  author    = (TextView)  findViewById(R.id.author);

        // Set the cover image
        coverIcon.setImageResource(thisBook.getImageId());

        // Display the title and author Text Views
        title.setText(thisBook.getTitle());
        author.setText(thisBook.getAuthor());

        // Set up the book information (separate function because it's extensive...)
        buildInfoString();

        // Assign the final string
        bookInfo.setText(bookDescription);
    }

    // Build a single string to represent the book's information
    private void buildInfoString() {
        // Assign the Title
        bookDescription =  "Title: "        + thisBook.getTitle()     + "\n";

        // And the author
        if (thisBook.getAuthor() == null) {
            bookDescription += "Author: (Unknown)\n";
        } else {
            bookDescription += "Author: "       + thisBook.getAuthor()    + "\n";
        }

        //'isRead' segment
        switch (thisBook.getReadStatus()) {
            case 0: bookDescription += "Not yet read.\n";
                break;
            case 1: bookDescription += "Reading right now.\n";
                break;
            case 2: bookDescription += "Already read.\n";
                break;
        }
        //finished 'isRead' segment
    }

    /**
     * Create the drop-down menu using a spinner. This will represent the read status
     *
     * Referenced: http://javatechig.com/android/android-spinner-example
     */
    public void setupSpinner() {
        // First, get the array of strings that will indicate the read status
        String[] readStatus = getResources().getStringArray(R.array.readStatus);

        // Declare the spinner
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        // Declare the adapter
        ArrayAdapter<String> spinnerAdapter;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, readStatus);

        // Set up the adapter and set it to the current read status of the book
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(thisBook.getReadStatus());

        // Set a listener that will allow the user to change the read status of the book
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Depending on which item is selected in the drop-down menu, a different
                // read status will be assigned
                TextView  bookInfo  = (TextView)  findViewById(R.id.bookInfo);

                switch(spinner.getSelectedItemPosition()) {
                    case 0:
                        thisBook.setReadStatus(0);
                        buildInfoString();
                        bookInfo.setText(bookDescription);
                        break;
                    case 1:
                        thisBook.setReadStatus(1);
                        buildInfoString();
                        bookInfo.setText(bookDescription);
                        break;
                    case 2:
                        thisBook.setReadStatus(2);
                        buildInfoString();
                        bookInfo.setText(bookDescription);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });
    }

    /**
     * Create a listener for the rating bar that will allow the user to set a rating for
     * this book
     */
    public void setupRatingBarListener() {
        // Create the rating bar and set it to the current book rating (not rated by default)
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating((float)thisBook.getRating());

        // Set a listener on the rating bar that will allow the user to change the rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                thisBook.setRating((int)rating);
            }
        });
    }

    /**
     * Self-explanatory
     */
    void setupListNoteButton() {
        Button btn = (Button) findViewById(R.id.notesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsActivity.this, ListNoteActivity.class);

                intent.putExtra("thisBook", thisBook);

                startActivity(intent);
            }
        });
    }
}
