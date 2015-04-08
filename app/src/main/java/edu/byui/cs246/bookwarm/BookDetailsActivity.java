package edu.byui.cs246.bookwarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import android.widget.Toast;

/**
 * This activity will display all information about the book in detail, and will allow
 * the user to access the Notes relating to the book, set the read status, and rate the book
 */
public class BookDetailsActivity extends ActionBarActivity {
    private static final String TAG_BOOK_DETAILS_ACTIVITY = "BookDetailsActivity";

    private Book thisBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Get the book from the main activity
        Book mainBook = (Book)getIntent().getSerializableExtra("thisBook");
        thisBook = Library.getInstance().getBook(mainBook.getId());

        // Get reference to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(thisBook.getTitle() + " - Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

        // If the user presses the back button in the menu bar
        if (id==android.R.id.home) {
            onBackPressed();
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
                finish();
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

            // Change the button's text
            Button button = (Button) findViewById(R.id.button3);
            button.setText("Remove From Favorites");

            // Add a heart
            ImageView heart = (ImageView) findViewById(R.id.fave);
            heart.setImageResource(R.drawable.ic_heart_icon);
            heart.setVisibility(View.VISIBLE);
            Toast.makeText(BookDetailsActivity.this, "Added \"" + thisBook.getTitle() + "\" to favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Remove the book from Favorites
            thisBook.setIsFavourite(false);
            Button button = (Button) findViewById(R.id.button3);
            button.setText("Add To Favorites");
            ImageView heart = (ImageView) findViewById(R.id.fave);
            heart.setVisibility(View.INVISIBLE);
            Toast.makeText(BookDetailsActivity.this, "Removed \"" + thisBook.getTitle() + "\" from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set the initial text of the button, depending on the status
     */
    public void setupFavoriteButton() {
        if (!thisBook.getIsFavourite()) {
            Button button = (Button)findViewById(R.id.button3);
            button.setText("Add To Favorites");
            ImageView heart = (ImageView) findViewById(R.id.fave);
            heart.setVisibility(View.INVISIBLE);
        } else {
            ImageView heart = (ImageView) findViewById(R.id.fave);
            heart.setImageResource(R.drawable.ic_heart_icon);
            heart.setVisibility(View.VISIBLE);
            Button button = (Button)findViewById(R.id.button3);
            button.setText("Remove From Favorites");
        }

    }

    /**
     * Delete the book from the library
     * Source referenced: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
     */
    public void removeBook(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to remove this book from your library?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the book from the database
                        Library.getInstance().deleteBook(thisBook);

                        // Return to the main activity
                        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        String removed = "\"" + thisBook.getTitle() + "\" has been removed from your library";
                        Toast.makeText(BookDetailsActivity.this, removed, Toast.LENGTH_LONG).show();
                        Log.i(TAG_BOOK_DETAILS_ACTIVITY, "Book removed");
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG_BOOK_DETAILS_ACTIVITY, "Book was not removed");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
