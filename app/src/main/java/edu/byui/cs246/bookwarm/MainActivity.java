package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    /*--------------------------------------------------------------------------------------------*/
    // Static variables
    private static final String TAG_MAIN_ACTIVITY = "MainActivity";  // Log tag
    public  static boolean      taskRunning       = false;           // Keep track of running tasks

    // Books
    public  Library             library = Library.getInstance();     // Singleton
    public  static List<Book>   listBooks;                           // List of books

    // Layout Views
    public  ProgressBar         progressBar;                         // The progress spinner
    public  TextView            loading;                             // "Loading" text
    public  ListView            list;                                // The Library ListView

    // Additional options
    private boolean             testingMode       = false;           // Create a dummy library
    private boolean             additionalLogging = false;           // Enable additional logging

    // Menu items
    private Menu                menu;                                // The overflow menu
    private MenuItem            sortModeTitle;                       // Title sort menu item
    private MenuItem            sortModeAuthor;                      // Author sort menu item
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the database
        library.instantiateDatabase(this);

        // If the library is empty, go to the special activity
        if (library.numBooks() == 0) {
            setContentView(R.layout.activity_main_empty);

            // If the library has no more than one book, sorting will not be needed
            if (library.numBooks() <=1) {
                library.setSortMode(0, this);
            }
        } else {
            setContentView(R.layout.activity_main);

            // Show the progress bar
            // Get reference to the progress bar and text view
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            loading = (TextView) findViewById(R.id.textView4);

            // Get the reference to the Action Bar
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("My Library");
            }

            /**
             * Because loading a large library may take a while, do it on an AsyncTask
             */
            class LibraryLoader extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    loading.setText("Loading library...");
                    loading.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected String doInBackground(String... params) {
                    try {
                        taskRunning = true;

                        // Add how many books to the library?
                        if (testingMode) {testAddBooks(300);}

                        // Set up the List View
                        if (library.getSortMode(MainActivity.this) == 0) {
                            setupCustomListView();
                        } else {
                            setupCustomListView();
                            checkAndSortBook(library.getSortMode(MainActivity.this));
                        }

                    } catch (Exception e) {
                        Log.e("LongOperation", "Interrupted", e);
                        return "Interrupted";
                    }
                    return "Finished executing AsyncTask: Load";
                }

                @Override
                protected void onPostExecute(String result) {
                    loading.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i(TAG_MAIN_ACTIVITY, result);
                    taskRunning = false;
                }
            }

            if (taskRunning) {
                return;
            }
            new LibraryLoader().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sortModeTitle = menu.findItem(R.id.sort_title);
        sortModeAuthor = menu.findItem(R.id.sort_author);
        this.menu = menu;

        if (library.getSortMode(MainActivity.this) == R.id.sort_title) {
            Log.i(TAG_MAIN_ACTIVITY, "Sort mode is currently by title");
            sortModeTitle.setChecked(true);
        } else if (library.getSortMode(MainActivity.this) == R.id.sort_author) {
            Log.i(TAG_MAIN_ACTIVITY, "Sort mode is currently by author");
            sortModeAuthor.setChecked(true);
        }
        return true;
    }

    @Override
    public void onBackPressed() {finish();}

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /**
         * Because sorting a large library may take a while, do it on an AsyncTask
         */
        class Sorter extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {
                taskRunning = true;
                try {
                    checkAndSortBook(item.getItemId());
                } catch (Exception e) {
                    Log.e("LongOperation", "Interrupted", e);
                    return "Interrupted";
                }
                return "Finished executing AsyncTask: Sort";
            }

            @Override
            protected void onPostExecute(String result) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.i(TAG_MAIN_ACTIVITY, result);
                taskRunning = false;
            }
        }

        // We don't want to start a new Async Task if we are already running one
        if (taskRunning) {
            Log.e(TAG_MAIN_ACTIVITY, "ERROR: AsyncTask already running. Cannot start new AsyncTask!");
            return false;
        }  else if (item.getItemId() == R.id.sort_title && library.getSortMode(MainActivity.this) == R.id.sort_title) {
            Log.e(TAG_MAIN_ACTIVITY, "Library is already sorted by title");
            return false;
        } else if (item.getItemId() == R.id.sort_author && library.getSortMode(MainActivity.this) == R.id.sort_author) {
            Log.e(TAG_MAIN_ACTIVITY, "Library is already sorted by author");
            return false;
        }

        // Execute the sort task
        new Sorter().execute();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple method used to test the limits of our library
     * @param books The number of books to add during this test
     */
    public void testAddBooks(int books) {

        // First, clear out the library
        library.clear();

        // Add however many books were specified
        String title;
        String author;
        for (int i = 1,  j = books; i <= books; ++i, --j) {
            title =  ""  + i;
            author = ""  + (char)j;
            Book book = new Book("Title " + title, author);
            book.setImageId(R.mipmap.ic_generic_cover);
            library.addBook(book);
        }
    }


    /**
     * Handle menu overflow input. If the user presses "Sort By Title" or
     * "Sort By Author," the library will be sorted and the sort mode will
     * be changed
     * @param id The ID of the overflow menu view that was pressed
     * @return Returns true if the ID matches an overflow menu item
     */
    private boolean checkAndSortBook(int id) {
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_new_book) {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.sort_title) {
            library.setSortMode(id, MainActivity.this);
            sortByTitle();
            return true;
        }

        if (id == R.id.sort_author) {
            library.setSortMode(id, MainActivity.this);
            sortByAuthor();
            return true;
        }
        return false;
    }

    /**
     * Implements insertion sort algorithm by book title
     */
    public static void insertionSortByTitle(){
        Book temp;
        for (int i = 1; i < listBooks.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(listBooks.get(j).getTitle().charAt(0) < listBooks.get(j-1).getTitle().charAt(0)){
                    temp = listBooks.get(j);
                    listBooks.set(j, listBooks.get(j-1));
                    listBooks.set(j-1, temp);
                }
            }
        }
    }

    /**
     * Implements insertion sort algorithm by book author
     */
    public static void insertionSortByAuthor(){
        Book temp;
        for (int i = 1; i < listBooks.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(listBooks.get(j).getAuthor().charAt(0) < listBooks.get(j-1).getAuthor().charAt(0)){
                    temp = listBooks.get(j);
                    listBooks.set(j, listBooks.get(j-1));
                    listBooks.set(j-1, temp);
                }
            }
        }
    }

    /**
     * Sorts Books By Title
     */
    public void sortByTitle() {
        if((list) != null) {
            Log.i(TAG_MAIN_ACTIVITY, "Sorting by title...");
            // Sort mode must be changed to title
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (menu != null) {
                        sortModeAuthor.setChecked(false);
                        sortModeTitle.setChecked(true);
                    }
                }
            });

            listBooks = library.getBooks();

            // Create a hash map to store each book's id
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < listBooks.size(); i++) {
                map.put(listBooks.get(i).getTitle(), listBooks.get(i).getId());
                if (additionalLogging) {
                    Log.i(TAG_MAIN_ACTIVITY, "Mapping " + listBooks.get(i).getTitle() + " to ID " + listBooks.get(i).getId());
                }
            }

            // Sort the list of books
            insertionSortByTitle();

            // Set up the custom adapter, and get the reference to the listview
            final CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, listBooks);
            list = (ListView) findViewById(R.id.listView);

            // Set the adapter on the main thread, and update the views
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    list.setAdapter(adapter);
                }
            });

            // Now delete the books from the database
            library.clearBooks();

            // The books will be re-inserted in the order that preserves their native IDs
            for(int i = 0; i < listBooks.size(); ++i) {
                listBooks.get(i).setId(map.get(listBooks.get(i).getTitle()));
                library.addBook(listBooks.get(i));
                if (additionalLogging) {
                    Log.i(TAG_MAIN_ACTIVITY, "Added " + library.getBook(map.get(listBooks.get(i).getTitle())).getTitle() + " with an ID of " + library.getBook(map.get(listBooks.get(i).getTitle())).getId());
                }
            }
        }
    }

    /**
     * Sorts Books By Author
     */
    public void sortByAuthor() {
        if((list) != null) {
            Log.i(TAG_MAIN_ACTIVITY, "Sorting by author...");

            // Sort mode is currently by title, so it must be changed to author
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (menu != null) {
                        sortModeAuthor.setChecked(true);
                        sortModeTitle.setChecked(false);
                    }
                }
            });

            listBooks = library.getBooks();

            // Create a hash map to store each book's id
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < listBooks.size(); i++) {
                map.put(listBooks.get(i).getTitle(), listBooks.get(i).getId());
                if (additionalLogging) {
                    Log.i(TAG_MAIN_ACTIVITY, "Mapping " + listBooks.get(i).getTitle() + " to ID " + listBooks.get(i).getId());
                }
            }

            // Sort the list of books
            insertionSortByAuthor();

            // Set up the custom adapter, and get the reference to the listview
            final CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, listBooks);
            list = (ListView) findViewById(R.id.listView);

            // Set the adapter on the main thread, and update the views
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    list.setAdapter(adapter);
                }
            });

            // Now delete the books from the database
            library.clearBooks();

            // The books will be re-inserted in the order that preserves their native IDs
            for(int i = 0; i < listBooks.size(); ++i) {
                listBooks.get(i).setId(map.get(listBooks.get(i).getTitle()));
                library.addBook(listBooks.get(i));
                if (additionalLogging) {
                    Log.i(TAG_MAIN_ACTIVITY, "Added " + library.getBook(map.get(listBooks.get(i).getTitle())).getTitle() + " with an ID of " + library.getBook(map.get(listBooks.get(i).getTitle())).getId());
                }
            }
        }
    }

    /**
     * Self-explanatory
     */
    private void setupCustomListView() {

        listBooks = library.getBooks();
        final CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, listBooks);
        list = (ListView) findViewById(R.id.listView);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                list.setAdapter(adapter);
            }
        });

        //The list listener itself, in all its glory
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create and start the new activity
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);

                //second part is basically just getting the corresponding book from the library,
                //the listView is just a visual representation of what's in the library, after all.
                intent.putExtra("thisBook", library.getBook(listBooks.get(position)));
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * When this method is called, go to the add book activity
     * @param view The view that was pressed
     */
    public void addBookActivity(View view) {
        // Go to the add book activity
        Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
        startActivity(intent);
        finish();
    }
}