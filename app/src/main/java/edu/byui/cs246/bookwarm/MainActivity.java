package edu.byui.cs246.bookwarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    /*--------------------------------------------------------------------------------------------*/
    public Library  library = Library.getInstance(); // Singleton
    public ListView list;
    public static List<Book> array;
    private static final String TAG_MAIN_ACTIVITY = "MainActivity";    // Log tag
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the database
        library.instantiateDatabase(this);

        // If the library is empty, go to the special activity
        if (library.numBooks() == 0) {
            setContentView(R.layout.activity_main_empty);
        } else {
            setContentView(R.layout.activity_main);

            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("My Library");
            }

            // Setting up the ListView on a separate thread
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Set up the List View
                            if (getSortIdFromPreferences() == 0) {
                                setupCustomListView();
                            } else {
                                setupCustomListView();
                                checkAndSortBook(getSortIdFromPreferences());
                            }
                        }
                    });
                }
            });

            thread.start();

            if (!thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
        library.setSortId(id);
        setSortIdToPreferences();
        checkAndSortBook(id);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the last sort method from Shared Preferences
     */
    private void setSortIdToPreferences() {
        SharedPreferences preferences = getSharedPreferences("sortId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Sort ID", library.getSortId());
        Log.i(TAG_MAIN_ACTIVITY, "Setting Sort ID to SharedPreferences...");
        editor.apply();
    }

    private int getSortIdFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("sortId", Context.MODE_PRIVATE);
        Log.i(TAG_MAIN_ACTIVITY, "Getting Sort ID from SharedPreferences...");
        return preferences.getInt("Sort ID", 0);
    }

    private boolean checkAndSortBook(int id) {
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_new_book) {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.sort_title) {
            Log.i(TAG_MAIN_ACTIVITY, "Sorting by title...");
            sortByTitle();
            return true;
        }

        if (id == R.id.sort_author) {
            Log.i(TAG_MAIN_ACTIVITY, "Sorting by author...");
            sortByAuthor();
            return true;
        }
        return false;
    }

    /**
     * Implements insertion sort algorithm
     */
    public static void insertionSortByTitle(){

        Book temp;
        for (int i = 1; i < array.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(array.get(j).getTitle().charAt(0) < array.get(j-1).getTitle().charAt(0)){
                    temp = array.get(j);
                    array.set(j, array.get(j-1));
                    array.set(j-1, temp);
                }
            }
        }
    }

    /**
     * Implements insertion sort algorithm
     */
    public static void insertionSortByAuthor(){

        Book temp;
        for (int i = 1; i < array.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(array.get(j).getAuthor().charAt(0) < array.get(j-1).getAuthor().charAt(0)){
                    temp = array.get(j);
                    array.set(j, array.get(j-1));
                    array.set(j-1, temp);
                }
            }
        }
    }

    /**
     * Sorts Books By Title
     */
    public void sortByTitle() {
        if((list) != null) {
            array = library.getBooks();
            insertionSortByTitle();

            // Sort by title and reset the adapter
            library.clear();
            for(int i = 0; i < array.size(); ++i) {
                library.addBook(array.get(i));
            }
            CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, library);
            list = (ListView) findViewById(R.id.listView);
            list.setAdapter(adapter);
        }
    }

    /**
     * Sorts Books By Author
     */
    public void sortByAuthor() {
        if((list) != null) {
            array = library.getBooks();
            insertionSortByAuthor();

            library.clear();
            for(int i = 0; i < array.size(); ++i) {
                library.addBook(array.get(i));
            }
            CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, library);
            list = (ListView) findViewById(R.id.listView);
            list.setAdapter(adapter);
        }
    }

    /**
     * Self-explanatory
     */
    private void setupCustomListView() {

        CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, library);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        //The list listener itself, in all its glory
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create and start the new activity
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);

                //second part is basically just getting the corresponding book from the library,
                //the listView is just a visual representation of what's in the library, after all.
                intent.putExtra("thisBook", library.getBooks().get((int)id));

                //ready, go.
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