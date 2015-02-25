package edu.byui.fed.bookwarm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    ListView list;
    String[] titles; //HERE IT IS. Add book titles to this String array.
    Integer[] imageId = { //Just one book for now. Adding titles without adding covers may break things.
            R.mipmap.generic_bookCover,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupCustomListView();
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
     * Self-explanatory
     */
    void setupCustomListView() {
        CustomList adapter = new CustomList(MainActivity.this, titles, imageId);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
}
