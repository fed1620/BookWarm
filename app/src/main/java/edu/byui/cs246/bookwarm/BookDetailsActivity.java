package edu.byui.cs246.bookwarm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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

    //The 'main' of this activity
    private void run() {
        thisBook = (Book)getIntent().getSerializableExtra("thisBook");

        setupDisplay();
        setupSpinner();
    }

    //Fill out the display's information
    private void setupDisplay() {
        //set the element variables
        ImageView coverIcon = (ImageView) findViewById(R.id.coverIcon);
        TextView  bookInfo  = (TextView)  findViewById(R.id.bookInfo);

        //set the cover image
        coverIcon.setImageResource(thisBook.getImageId());
        //set up the book information (separate function because it's extensive...)
        buildInfoString();

        //assign the final string
        bookInfo.setText(bookDescription);
    }

    //build a single string to represent the book's information
    private void buildInfoString() {
        bookDescription =  "Title: "        + thisBook.getTitle()     + "\n";
        bookDescription += "Author: "       + thisBook.getAuthor()    + "\n";
        bookDescription += "Publisher: "    + thisBook.getPublisher() + "\n";
        //'isRead' segment
        switch (thisBook.getReadStatus()) {
            case -1: bookDescription += "Not yet read.\n";
                break;
            case  0: bookDescription += "Reading right now.\n";
                break;
            case  1: bookDescription += "Already read.\n";
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

        // Declare the spinner and the array adapter
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, readStatus);

        // Set up the adapter
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Depending on which item is selected in the drop-down menu, a different
                // read status will be assigned
                switch(spinner.getSelectedItemPosition()) {
                    case 0: thisBook.setReadStatus(-1);
                    break;
                    case 1: thisBook.setReadStatus(0);
                    break;
                    case 2: thisBook.setReadStatus(1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });
    }
}
