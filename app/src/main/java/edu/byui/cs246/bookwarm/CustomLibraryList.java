package edu.byui.cs246.bookwarm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The custom ArrayAdapter we'll use to display the library of books
 * Reference: http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */
public class CustomLibraryList extends ArrayAdapter<String> {
    private final Activity context;
    private final Library  library;

    /**
     * Constructor for the class
     * @param context context in which it is used
     * @param library the library to be loaded
     */
    public CustomLibraryList(Activity context, Library library) {
        super(context, R.layout.library_list_single, library.getBookTitles());

        this.context = context;
        this.library = library;
    }

    /**
     * Decide what gets shown in the ListView
     * @param position The location of the user's 'click'.
     * @param view     Mainly for resuability, but largely obselete in this context. We just need to meet the @Override conditions.
     * @param parent   In this case, the parent is the List containing the Book view.
     * @return         Returns a view of the customized Book view in the list row.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //expand the list
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.library_list_single, null, true);

        //retrieve the XML resources and set them up to be changed
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        //change em
        txtTitle.setText(library.getBookTitles()[position]);
        imageView.setImageResource(library.getImageIds()[position]);

        return rowView;
    }
}