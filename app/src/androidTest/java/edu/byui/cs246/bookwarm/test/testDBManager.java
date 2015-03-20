package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

import edu.byui.cs246.bookwarm.Book;
import edu.byui.cs246.bookwarm.DBManager;

/**
 * Tests our SQLite database class
 */
public class testDBManager extends AndroidTestCase {
    private static final String TAG_DB_MANAGER   = "DBManager";

    /**
     * Adds two books to the database
     */
    public void testAddBook() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());

        // Create a book
        Book book = new Book("Head First Java", "Bert Bates and Kathy Sierra");

        // Add the book to the database
        db.addBook(book);

        // Create a bad book
        Book bookNull = null;

        // Add the bad book
        // noinspection ConstantConditions
        db.addBook(bookNull);
    }

    /**
     * Add a book, and then see whether or not it is in the database
     */
    public void testContainsBook() {
        // Instantiate a database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Where the Wild Things Are", "Maurice Sendak");

        // Add it to the database
        db.addBook(book);

        // Check if the book is in the database
        assertEquals(true, db.containsBook(book));
    }

    /**
     * Add a book to the database, and then remove it
     */
    public void testDeleteBook() {
        // Instantiate a database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Charlie and the Chocolate Factory", "Roald Dahl");

        // Add it to the database
        db.addBook(book);

        // Get the book
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));

        // Remove it from the database
        db.deleteBook(book);

        // Try to get the book
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));
    }

    /**
     * Get a book from the database
     */
    public void testGetBook() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Head First Design Patterns", "Bert Bates and Kathy Sierra");

        // Add the books to the database
        db.addBook(book);
        Log.i(TAG_DB_MANAGER, book.getTitle() + " has an ID of: " + book.getId());

        // Attempt to get the book
        Book fetchedBook = db.getBook(book.getId());

        // Display the book
        Log.i(TAG_DB_MANAGER, "Retrieved Book: " + fetchedBook.toString());
    }

    /**
     * Get a list of Books from the database
     */
    public void testGetBooks() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create three textbooks
        Book book1 = new Book("What's the Worst That Could Happen", "Greg Craven");
        Book book2 = new Book("Writing With Style", "John Trimble");
        Book book3 = new Book("Procedural Programming in C++", "James Helfrich");

        // Add the books to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);

        // Attempt to get the books
        List<Book> books = db.getBooks();

        // Display the retrieved book list
        Log.i(TAG_DB_MANAGER, "Retrieved " + books.size() + " books from the database:");
        for (Book book : books) {
            Log.i(TAG_DB_MANAGER, book.toString());
        }
    }

    /**
     * Individually get three books from the database
     */
    public void testGetThreeBooks() {
        // Instantiate our test database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create three books
        Book book1 = new Book("The Hunger Games", "Suzanne Collins");
        Book book2 = new Book("Catching Fire", "Suzanne Collins");
        Book book3 = new Book("Mockingjay", "Suzanne Collins");

        // Add the books to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);
        // What are their IDs?
        Log.i(TAG_DB_MANAGER, book1.getTitle() + " has an ID of: " + book1.getId());
        Log.i(TAG_DB_MANAGER, book2.getTitle() + " has an ID of: " + book2.getId());
        Log.i(TAG_DB_MANAGER, book3.getTitle() + " has an ID of: " + book3.getId());

        // Attempt to get the books
        Book fetchedBook1 = db.getBook(book1.getId());
        Book fetchedBook2 = db.getBook(book2.getId());
        Book fetchedBook3 = db.getBook(book3.getId());

        // Display the book
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook1.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook2.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook3.toString());
    }

    public void testSize() {
        // Instantiate a database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Check the size (should be 0)
        assertEquals(0, db.size());

        // Create books to go into the database
        Book book1 = new Book("Divergent", "Veronica Roth");
        Book book2 = new Book("Insurgent", "Veronica Roth");
        Book book3 = new Book("Allegiant", "Veronica Roth");

        // Add the books to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);

        // Check the size (should be 3)
        assertEquals(3, db.size());
    }

    /**
     * Add a book to the database, and then update it
     */
    public void testUpdateBook() {
        // Instantiate a database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Ender's Game", "Orson Scott Card");

        // Insert it into the database
        db.addBook(book);

        // Get the book
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));

        // Update the book
        book.setTitle("Ender's Shadow");
        db.updateBook(book);

        // Get the book again
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));
    }
}
