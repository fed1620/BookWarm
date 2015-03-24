package edu.byui.cs246.bookwarm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jake on 3/16/2015.
 */
public class bookSearchInputActivity extends ActionBarActivity {
    //variables go here
    EditText titleSearchInput;
    Button searchButton;
    GoogleBooksMessenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search_input);

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

    /**
     * Our new 'main'. Less cluttered this way.
     */
    private void run() {
        setupVariables();
        setupSearchButtonListener();
    }

    /**
     * Function to intialize any class variables.
     */
    private void setupVariables() {
        titleSearchInput = (EditText) findViewById(R.id.titleSearchInput);
        searchButton     = (Button)   findViewById(R.id.searchButton);
        messenger        = new GoogleBooksMessenger();
    }

    /**
     * Self-explanatory
     */
    private void setupSearchButtonListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is wehere the magic happens.
                messenger.searchGoogleBooks(titleSearchInput.getText().toString());
            }
        });
    }
}
