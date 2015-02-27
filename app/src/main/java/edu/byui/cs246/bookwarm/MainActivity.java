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
    /*--------------------------------------------------------------------------------------------*/
    public static Library library = new Library(); // ** Needs to be static, so that a new library
    public ListView  list;                         // is not created every time we switch back to
    public Integer[] imageId;                      // the main activity
    public String[]  bookTitles;
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The library should be initially 'created' (or in the future: 'loaded' from a file)
        // ONLY once
        if (library.numBooks() == 0) {
            createLibrary(library);
        }

        // If any new books have been entered by the user, add that new book to our library.
        addNewBook(library);

        // Because our custom list view needs the book information to be in array format, we will
        // kindly ask our library to give us all of the necessary book information in array format!
        getArrayElements(library);

        // Set up the List View and the Menu button
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

    /**
     * Create (In the future: load from a file) the user's library
     * This method should only be called one time
     *
     * @param library The library object that will store the user's library of books
     */
    public void createLibrary(Library library) {
        // ** For the time being, we are simply creating a hard-coded library
        // ** In the future, this will be loaded from a file!

        // To Kill A Mockingbird
        Book book1 = new Book();
        book1.setTitle("To Kill a Mockingbird");
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
    }

    /**
     * If a new book object was passed to this activity via an intent, this method
     * will simply add that new book to the user's library
     *
     * (NOTE: This method will likely be made obsolete in the future, when instead of
     * passing the new book back to this activity, we will simply write the book
     * to the library file and it will automatically be loaded along with the other ones)
     *
     * @param library The user's library
     */
    public void addNewBook(Library library) {
        // Check for a new book that may have been added
        Book newBook = (Book)getIntent().getSerializableExtra("newBook");
        if (newBook != null) {
            library.addBook(newBook);
        }
    }

    /**
     * All of the book information that we choose to display in our custom list view will
     * be retrieved in array format
     *
     * @param library The user's library
     */
    public void getArrayElements(Library library) {
        // Get the necessary array elements from the library (we need this for our custom list view)
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