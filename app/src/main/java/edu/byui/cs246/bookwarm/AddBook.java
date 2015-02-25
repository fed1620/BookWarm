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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Set up the button that will allow us to add a new book
        setupAddBookButton();
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
     * Add a new book to the library
     */
    void setupAddBookButton() {
        Button btn = (Button) findViewById(R.id.addBook);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title of the new book
                EditText bookTitle = (EditText)findViewById(R.id.title);
                String title = bookTitle.getText().toString();

                // The new book that will be added to the library
                Book newBook = new Book();
                newBook.setTitle(title);
                newBook.setImageId(R.mipmap.ic_generic_cover);

                // TODO: Add the new book to the same library being used in MainActivity.java

                // Returns to the main activity
                startActivity(new Intent(AddBook.this, MainActivity.class));
            }
        });
    }
}
