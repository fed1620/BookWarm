package edu.byui.cs246.bookwarm;
//some code lovingly ripped off from: http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The custom ArrayAdapter we'll use to display the library of books
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity  context;
    private final Library library;

    /**
     * Constructor for the class
     * @param context context in which it is used
     * @param library the library to be loaded
     */
    public CustomList(Activity context, Library library) {
        super(context, R.layout.list_single, library.getBookTitles());

        this.context = context;
        this.library = library;
    }

    /**
     * Decide what gets shown in the ListView
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //expand the list
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);

        //retrieve the XML resources and set them up to be changed
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        //change em
        txtTitle.setText(library.getBookTitles()[position]);
        imageView.setImageResource(library.getImageIds()[position]);

        return rowView;
    }
}