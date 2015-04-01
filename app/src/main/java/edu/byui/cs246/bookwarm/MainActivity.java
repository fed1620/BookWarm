package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    /*--------------------------------------------------------------------------------------------*/
    public Library  library = Library.getInstance(); // Singleton
    public ListView list;
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

            // Set up the List View
            setupCustomListView();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_new_book) {
            Intent intent = new Intent(MainActivity.this, AddBookMethod.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.sort) {
            sortBooks();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void bubble_srt(List<Book> array) {
        int n = array.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (array.get(i).getTitle().charAt(0) > array.get(k).getTitle().charAt(0)) {
                    swapNumbers(i, k, array);
                }
            }
        }
    }

    private static void swapNumbers(int i, int j, List<Book> array) {

        Book temp;
        temp = array.get(i);
        array.get(i).setTitle(array.get(j).getTitle());
        array.get(j).setTitle(temp.getTitle());
    }

    /**
     *
     */
    void sortBooks() {
        List<Book> temp = (List<Book>) list.getAdapter(); // TODO: This line causes a crash when the user presses "Sort"

        bubble_srt(temp);

        ListAdapter adapter = (ListAdapter) temp;
        list.setAdapter(adapter);
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
            }
        });
    }

    /**
     * When this method is called, go to the add book activity
     * @param view The view that was pressed
     */
    public void addBookActivity(View view) {
        // Go to the add book activity
        Intent intent = new Intent(MainActivity.this, AddBookMethod.class);
        startActivity(intent);
    }
}