package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;

import edu.byui.cs246.bookwarm.Book;
import edu.byui.cs246.bookwarm.DBManager;
import edu.byui.cs246.bookwarm.R;

/**
 * Tests our SQLite database class
 */
public class testDBManager extends AndroidTestCase {
    public void test1() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());

        // Create a book
        Book book = new Book();
        book.setTitle("Head First Java");
        book.setAuthor("Bert Bates and Kathy Sierra");
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
}
