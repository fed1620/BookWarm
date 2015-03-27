package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AddBookMethod extends ActionBarActivity {

    private Book thisBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_method);

        setupButtons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book_method, menu);
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
     * Macro for setting up all the buttons here.
     */
    private void setupButtons() {
        setupAddBookManuallyButton();
        setupAddBookGoogleButton();
    }

    /**
     * SETUP ADD NOTE BUTTON
     * Creates ADD NOTE ACTIVITY class by intent
     **/
    private void setupAddBookManuallyButton() {
        Button btn = (Button) findViewById(R.id.manually);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent
                Intent intent = new Intent(AddBookMethod.this, AddBookActivity.class);

                intent.putExtra("thisBook", thisBook);

                // run intent
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up a listener for the Google button
     */
    private void setupAddBookGoogleButton() {
        Button btn = (Button) findViewById(R.id.google);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
