package edu.byui.cs246.bookwarm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jake on 2/28/2015.
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
}
