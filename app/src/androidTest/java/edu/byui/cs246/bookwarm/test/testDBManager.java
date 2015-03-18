package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;
import android.util.Log;

import edu.byui.cs246.bookwarm.Book;
import edu.byui.cs246.bookwarm.DBManager;
import edu.byui.cs246.bookwarm.R;

/**
 * Tests our SQLite database class
 */
public class testDBManager extends AndroidTestCase {
    private static final String TAG_DB_MANAGER   = "DBManager";
    /*
    public void testAddBook() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());

        // Create a book
        Book book = new Book("Head First Java", "Bert Bates and Kathy Sierra");
        book.setImageId(R.mipmap.ic_generic_cover);
        book.setReadStatus(2);

        // Add the book to the database
        db.addBook(book);

        // Create a bad book
        Book bookNull = null;

        // Add the bad book
        // noinspection ConstantConditions
        db.addBook(bookNull);
    }


    public void testGetBook() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());

        // Create a book
        Book book = new Book("Head First Java", "Bert Bates and Kathy Sierra");
        book.setImageId(R.mipmap.ic_generic_cover);
        book.setReadStatus(2);

        // Add the books to the database
        db.addBook(book);

        // Attempt to get the book
        Book fetchedBook1 = db.getBook(book.getTitle());

        // Display the book
        Log.i(TAG_DB_MANAGER, "Retrieved book: " + fetchedBook1.toString());
    }*/

    // Test this after testGetBook() is working


    public void testGetTwoBooks() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());

        // Create three books
        Book book1 = new Book("Head First Java", "Bert Bates and Kathy Sierra");
        book1.setImageId(R.mipmap.ic_generic_cover);
        book1.setReadStatus(2);

        Book book2 = new Book("Head First Design Patterns", "Bert Bates and Kathy Sierra");
        book2.setImageId(R.mipmap.ic_generic_cover);
        book2.setReadStatus(1);

        Book book3 = new Book("Procedural Programming in C++", "James Helfrich");
        book2.setImageId(R.mipmap.ic_generic_cover);
        book2.setReadStatus(1);

        // Add the books to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);

        // Attempt to get the books
        Book fetchedBook1 = db.getBook(book1.getTitle());
        Book fetchedBook2 = db.getBook(book2.getTitle());
        Book fetchedBook3 = db.getBook(book3.getTitle());

        // Display the book
        Log.i(TAG_DB_MANAGER, "Retrieved book: " + fetchedBook1.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book: " + fetchedBook2.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book: " + fetchedBook3.toString());

    }

}
