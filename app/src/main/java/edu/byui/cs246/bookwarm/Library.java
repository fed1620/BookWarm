package edu.byui.cs246.bookwarm;

import android.content.Context;
import android.util.Log;

import java.util.List;


/**
 * A library functions as a wrapper for our SQLite database of Book objects
 */
public class Library {
    /** Log tag */
    private static final String TAG_LIBRARY = "Library";

    // Eager Singleton
    private static final Library instance = new Library();
    private Library() {}
    public static Library getInstance() {return instance;}

    // The database that our library will use
    private DBManager  db;

    /**
     * Add a book object to the list of books
     * @param book The book to be added
     */
    public void addBook(Book book) {
        if (book == null) {
            Log.e(TAG_LIBRARY, "ERROR: Cannot add a book with value: null");
            return;
        } else if (book.getTitle() == null || book.getAuthor() == null) {
            Log.e(TAG_LIBRARY, "ERROR: Cannot add a book with null title/author");
            return;
        }
        this.db.addBook(book);
    }

    /**
     * Clear the library of all books
     */
    public void clear() {this.db.clear();}

    /**
     * Does the book already exist in our database?
     * @param book The book to be searched for
     * @return Returns true if the book is already in the database
     */
    public boolean contains(Book book) { return this.db.containsBook(book);}

    /**
     * Display all of the books in the library
     */
    public void display() {
        Log.i(TAG_LIBRARY, "Library contains the following " + numBooks() + " book(s): ");
        Log.i(TAG_LIBRARY, "_______________________________");
        for (Book book : getBooks()) {
            Log.i(TAG_LIBRARY, book.toString());
        }
        Log.i(TAG_LIBRARY, "-------------------------------");
    }

    /**
     * Delete a book object from the list of books
     * @param condemnedBook The book to be removed
     */
    public void deleteBook(Book condemnedBook) {
        if (condemnedBook == null) {
            Log.e(TAG_LIBRARY, "ERROR: Cannot delete a book with value: null");
            return;
        }
        this.db.deleteBook(condemnedBook);
    }

    /**
     * This method will return an array of every book's favorite status
     * @return returns an array of booleans (favorite status)
     */
    public boolean[] getFavorites() {
        // Allocate a new array according to how many books we have
        boolean[] favorites = new boolean[numBooks()];

        // Go through our list of books and get each favorite status
        for (int i = 0; i < numBooks(); ++i) {
            favorites[i] = getBooks().get(i).getIsFavourite();
        }
        return favorites;
    }

    /**
     * Get a book from the database
     * @return Returns a book
     */
    public Book getBook(int id) {
        if (this.db == null) {
            Log.e(TAG_LIBRARY, "ERROR: Database object is null");
            return null;
        }
        return this.db.getBook(id);
    }

    /**
     * Get a note from the database
     * @return Returns a note
     */
    public Note getNote(int id) {
        if (this.db == null) {
            Log.e(TAG_LIBRARY, "ERROR: Database object is null");
            return null;
        }
        return this.db.getNote(id);
    }

    /**
     * A getter that will return our list of books
     * @return Returns List<Book> books
     */
    public List<Book> getBooks() {
        if (this.db == null) {
            Log.e(TAG_LIBRARY, "ERROR: Database object is null");
            return null;
        }
        return this.db.getBooks();
    }

    /**
     * This method will return an array of every book title in our Library
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
     * Instantiate the database from the main activity
     */
    public void instantiateDatabase(Context context) {
        if (db == null) {
            db = new DBManager(context);
        }
    }

    /**
     * How many books are in our library
     * @return Returns an integer representing the number of books in the library
     */
    public int numBooks() {
        if (getBooks().size() < 0) {
            Log.e(TAG_LIBRARY, "ERROR: Library contains invalid number of books");
            return 0;
        }
        return this.db.size();
    }

    /**
     * Deletes a note from the database
     * @param note The note to be deleted
     */
    public void removeNote(Note note) {
        db.deleteNote(note);
    }

    /**
     * Update the book in the database
     * @param book The book to be updated
     */
    public void updateBook(Book book) {this.db.updateBook(book);}

    /**
     * Update the note in the database
     * @param note The note to be updated
     */
    public void updateNote(Note note) {this.db.updateNote(note);}

}
