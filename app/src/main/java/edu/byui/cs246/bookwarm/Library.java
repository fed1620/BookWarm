package edu.byui.cs246.bookwarm;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * A library is a list of books
 */
public class Library {
    /** Log tag */
    private static final String TAG_LIBRARY = "Library";

    // Eager Singleton
    private static final Library instance = new Library();
    private Library() {}
    public static Library getInstance() {return instance;}

    // Instantiate the database
    private DBManager db = new DBManager(App.getAppContext());
    private List<Book> books = new ArrayList<>();

    /**
     * Add a book object to the list of books
     * @param book The book to be added
     */
    public void addBook(Book book) {
        if (book == null) {
            System.out.println("ERROR: Cannot add a book with value: null");
            return;
        }
        books.add(book);
    }

    /**
     * Add a book object to the database of books
     * @param book The book to be added
     */
    public void addBookToDatabase(Book book) {
        if (book == null) {
            System.out.println("ERROR: Cannot add a book with value: null");
            return;
        }
        books.add(book);
    }

    /**
     * Delete a book object from the list of books
     * @param condemnedBook The book to be removed
     */
    public void deleteBook(Book condemnedBook) {
        if (condemnedBook == null) {
            System.out.println("ERROR: Cannot delete a book with value: null");
            return;
        }
        books.remove(condemnedBook);
    }

    /**
     * How many books are in our library
     *
     * @return Returns an integer representing the number of books in the library
     */
    public int numBooks() {
        if (getBooks().size() < 0) {
            System.out.println("ERROR: Library contains invalid number of books");
            return 0;
        }
        return getBooks().size();
    }

    /**
     * A getter that will return our list of books
     *
     * @return Returns List<Book> books
     */
    public List<Book> getBooks() {
        if (books == null) {
            System.out.println("ERROR: list of books is null");
            return null;
        }
        return this.books;
    }

    /**
     * This method will return an array of every book title in our Library
     *
     * @return returns an array of Strings (book titles)
     */
    public String[] getBookTitles() {
        // Allocate a new array according to how many books we have
        String[] bookTitles = new String[numBooks()];

        // Go through our list of books and get each title
        for (int i = 0; i < numBooks(); ++i) {
            bookTitles[i] = getBooks().get(i).getTitle();
        }
        return bookTitles;
    }

    /**
     * This method will return an array of image ID numbers
     *
     * @return returns an array of ints (image IDs)
     */
    public Integer[] getImageIds() {
        // Allocate a new array according to how many books we have
        Integer[] imageIds = new Integer[numBooks()];

        // Go through our list of books and get each title
        for (int i = 0; i < numBooks(); ++i) {
            imageIds[i] = getBooks().get(i).getImageId();
        }
        return imageIds;
    }

    /**
     * When the book passed in matches a book that is in our library, update the
     * book to reflect any changes that have been made
     *
     * @param book The book to be updated
     */
    public void updateBookInfo(Book book) {
        for (int i = 0; i < numBooks(); ++i) {
            if (book.getTitle().equals(books.get(i).getTitle())) {

                // Change the read status, the rating, and favorite status of
                // the book in the library
                books.get(i).setReadStatus(book.getReadStatus());
                books.get(i).setRating(book.getRating());
                books.get(i).setIsFavourite(book.getIsFavourite());
            }
        }
    }

    /**
     * Display all of the books in the library
     */
    public void display() {
        for (Book book : books) {
            Log.i(TAG_LIBRARY, book.toString());
        }
    }
}
