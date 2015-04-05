package edu.byui.cs246.bookwarm;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * A library functions as a wrapper for our SQLite database of Book objects
 */
public class Library {
    /** Log tag */
    private static final String TAG_LIBRARY = "Library";

    // Eager Singleton -> (There can only be one!)
    private static final Library instance = new Library();
    private Library() {}
    public static Library getInstance() {return instance;}

    // The database that our library will use
    private DBManager  db;

    // What is the library sorted by?
    private int sortId = 0;

    // Getter and setter for sortId
    public void setSortId(int sortId) {this.sortId = sortId;}
    public int  getSortId()           {return this.sortId;}

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
    public Boolean[] getFavorites() {
        // Get the list of books
        List<Book> books = getBooks();
        List<Boolean> favorites = new ArrayList<>();

        // Go through our list of books and get each image
        for (int i = 0; i < books.size(); ++i) {
            favorites.add(books.get(i).getIsFavourite());
        }

        // Convert the list to an array
        return favorites.toArray(new Boolean[favorites.size()]);
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
        // Get the list of books
        List<Book> books = getBooks();
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
        List<Book> books = getBooks();
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
        List<Book> books = getBooks();
        List<String> authors = new ArrayList<>();

        // Go through our list of books and get each author
        for (int i = 0; i < books.size(); ++i) {
            authors.add(books.get(i).getAuthor());
        }

        // Convert the list to an array
        return authors.toArray(new String[authors.size()]);
    }

    /**
     * This method will return an array of read statuses
     * @return returns an array of ints (read status)
     */
    public Integer[] getReadStatuses() {
        // Get the list of books
        List<Book> books = getBooks();
        List<Integer> readStatusArray = new ArrayList<>();

        // Go through our list of books and get each read status
        for (int i = 0; i < books.size(); ++i) {
            readStatusArray.add(books.get(i).getImageId());
        }

        // Convert the list to an array
        return readStatusArray.toArray(new Integer[readStatusArray.size()]);
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
    public int numBooks() {return this.db.size();}

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
