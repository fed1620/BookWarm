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
    private final String[]  titles;
    private final Integer[] imageId;

    /**
     * Constructor for the CustomList class
     * @param context
     * @param titles  The titles for all the books
     * @param imageId The book covers. (Until we are more pro, just use the generic covers I put in mipmap/covers
     */
    public CustomList(Activity context, String[] titles, Integer[] imageId) {
        super(context, R.layout.list_single, titles);

        this.context = context;
        this.titles  = titles;
        this.imageId = imageId;
    }

    /**
     * Not sure why this is necessary, probably something to do with ListView.setAdapter()
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
        txtTitle.setText(titles[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}