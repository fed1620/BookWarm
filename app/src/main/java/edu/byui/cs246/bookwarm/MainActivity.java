package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    // The member variables we need for the different methods in this activity
    public ListView list;
    public String[] bookTitles;
    public Integer[] imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadLibrary();
        setupCustomListView();
        setupMenuButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void loadLibrary() {
        Library library = new Library();  // For the time being, we are simply creating a library
                                          // In the future, this will be loaded from a file

        // To Kill A Mockingbird
        Book book1 = new Book();
        book1.setTitle("To Kill A Mockingbird");
        book1.setImageId(R.mipmap.ic_generic_cover);

        // The Great Gatsby
        Book book2 = new Book();
        book2.setTitle("The Great Gatsby");
        book2.setImageId(R.mipmap.ic_generic_cover);

        // Pride and Prejudice and Zombies
        Book book3 = new Book();
        book3.setTitle("Pride and Prejudice and Zombies");
        book3.setImageId(R.mipmap.ic_generic_cover);

        // Add all of our hard-coded books to the library
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Get the necessary array elements from the library
        bookTitles = library.getBookTitles();
        imageId = library.getImageIds();
    }

    /**
     * Self-explanatory
     */
    void setupCustomListView() {
        CustomList adapter = new CustomList(MainActivity.this, bookTitles, imageId);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    /**
     * Also self-explanatory
     */
    void setupMenuButton() {
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens the menu activity
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }
}
