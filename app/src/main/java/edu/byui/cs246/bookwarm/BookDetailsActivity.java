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
    private Book   thisBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // jumping to a run function because onCreate is cluttered as fudge
        run();
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
        // First, update the book
        Library.getInstance().updateBook(thisBook);

        // Then, return to the main activity
        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * The 'main' of this activity.
     */
    private void run() {
        // Do nothing with an invalid book
        if (getIntent().getSerializableExtra("thisBook") == null) {return;}

        // Get the book from the main activity
        thisBook = (Book)getIntent().getSerializableExtra("thisBook");

        // Set up the various layout elements
        setupDisplay();
        setupSpinner();
        setupRatingBarListener();
        setupListNoteButton();
        setupFavoriteButton();
    }

    /**
     * Builds the display to show the book details.
     */
    private void setupDisplay() {
        // Set the element variables
        ImageView coverIcon = (ImageView) findViewById(R.id.coverIcon);
        TextView  title     = (TextView)  findViewById(R.id.title);
        TextView  author    = (TextView)  findViewById(R.id.author);

        // Set the cover image
        coverIcon.setImageResource(thisBook.getImageId());

        // Display the title and author Text Views
        title.setText(thisBook.getTitle());
        author.setText(thisBook.getAuthor());
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
                switch(spinner.getSelectedItemPosition()) {
                    case 0:
                        thisBook.setReadStatus(0);
                        break;
                    case 1:
                        thisBook.setReadStatus(1);
                        break;
                    case 2:
                        thisBook.setReadStatus(2);
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
    public void setupListNoteButton() {
        Button btn = (Button) findViewById(R.id.notesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, update the book
                Library.getInstance().updateBook(thisBook);

                // Then, pass the book to the Note Activity
                Intent intent = new Intent(BookDetailsActivity.this, ListNoteActivity.class);
                intent.putExtra("thisBook", Library.getInstance().getBook(thisBook.getId()));
                startActivity(intent);
            }
        });
    }

    /**
     * When the user clicks the button, set the favorite status accordingly
     * @param view The view that is clicked
     */
    public void changeFavoriteStatus(View view) {
        if (!thisBook.getIsFavourite()) {
            // Add the book as a Favorite
            thisBook.setIsFavourite(true);
            Button button = (Button) findViewById(R.id.button3);
            button.setText("Remove From Favorites");
        } else {
            // Remove the book from Favorites
            thisBook.setIsFavourite(false);
            Button button = (Button) findViewById(R.id.button3);
            button.setText("Add To Favorites");
        }
    }

    /**
     * Set the initial text of the button, depending on the status
     */
    public void setupFavoriteButton() {
        if (!thisBook.getIsFavourite()) {
            Button button = (Button)findViewById(R.id.button3);
            button.setText("Add To Favorites");
        } else {
            Button button = (Button)findViewById(R.id.button3);
            button.setText("Remove From Favorites");
        }

    }

    /**
     * Delete the book from the library
     */
    public void removeBook(View view) {
        // Delete the book from the database
        Library.getInstance().deleteBook(thisBook);

        // Return to the main activity
        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
