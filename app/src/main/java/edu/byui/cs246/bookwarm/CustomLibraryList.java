package edu.byui.cs246.bookwarm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The custom ArrayAdapter we'll use to display the library of books
 * Reference: http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */
public class CustomLibraryList extends ArrayAdapter<String> {
    private final Activity context;
    private final List<Book> books;

    /**
     * Constructor for the class
     * @param context context in which it is used
     * @param books the List of Books
     */
    public CustomLibraryList(Activity context, List<Book> books) {
        super(context, R.layout.library_list_single, Library.getInstance().getBookTitles());

        this.context = context;
        this.books = books;
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
        TextView txtTitle   = (TextView)  rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ImageView heartView = (ImageView) rowView.findViewById(R.id.heartImg);

        //change em
        txtTitle.setText(setupTitleAndAuthor(position));
        imageView.setImageResource(getImageIds()[position]);
        checkAndRunForFavorites(heartView, position);

        return rowView;
    }

    /**
     * This method will return an array of every book title in our Library
     * @return returns an array of Strings (book titles)
     */
    public String[] getBookTitles() {
        List<String> titles = new ArrayList<>();

        // Create a list of the titles
        for (int i = 0; i < books.size(); ++i) {
            titles.add(books.get(i).getTitle());
        }

        // Convert the list to an array
        return titles.toArray(new String[titles.size()]);
    }

    /**
     * This method will return an array of image ID numbers
     * @return returns an array of ints (image IDs)
     */
    public Integer[] getImageIds() {
        // Get the list of books
        List<Integer> images = new ArrayList<>();

        // Go through our list of books and get each image
        for (int i = 0; i < books.size(); ++i) {
            images.add(books.get(i).getImageId());
        }

        // Convert the list to an array
        return images.toArray(new Integer[images.size()]);
    }

    /**
     * This method will return an array of authors
     * @return returns an array of Strings (authors)
     */
    public String[] getAuthors() {
        // Get the list of books
        List<String> authors = new ArrayList<>();

        // Go through our list of books and get each author
        for (int i = 0; i < books.size(); ++i) {
            authors.add(books.get(i).getAuthor());
        }

        // Convert the list to an array
        return authors.toArray(new String[authors.size()]);
    }

    /**
     * This method will return an array of every book's favorite status
     * @return returns an array of booleans (favorite status)
     */
    public Boolean[] getFavorites() {
        // Get the list of books
        List<Boolean> favorites = new ArrayList<>();

        // Go through our list of books and get each image
        for (int i = 0; i < books.size(); ++i) {
            favorites.add(books.get(i).getIsFavourite());
        }

        // Convert the list to an array
        return favorites.toArray(new Boolean[favorites.size()]);
    }

    /**
     * Checks to see if the book is favorited, and displays the icon accordingly
     * @param heartView
     * @param position
     */
    private void checkAndRunForFavorites(ImageView heartView, int position) {
        if (getFavorites()[position]){
            heartView.setImageResource(R.drawable.ic_heart_icon);
        }
    }

    /**
     * Sets up the segment for the Title and Author
     * @param position
     * @return
     */
    private String setupTitleAndAuthor(int position) {
        return getBookTitles()[position] + "\n" + getAuthors()[position];
    }
}