package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

import edu.byui.cs246.bookwarm.Book;
import edu.byui.cs246.bookwarm.DBManager;
import edu.byui.cs246.bookwarm.R;

/**
 * Tests our SQLite database class
 */
public class testDBManager extends AndroidTestCase {
    private static final String TAG_DB_MANAGER   = "DBManager";

    /**
     * Adds two books to the database
     */
    public void testAddBook() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());

        // Instantiate two the books
        Book book1  = new Book("Head First Java", "Bert Bates and Kathy Sierra");
        Book book2  = new Book("Head First Design Patterns", "Bert Bates and Kathy Sierra");

        // Add the books to the database
        db.addBook(book1);
        db.addBook(book2);
    }

    /**
     * See whether or not a given book is in the database
     */
    public void testContainsBook() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Where the Wild Things Are", "Maurice Sendak");

        // Add it to the database
        db.addBook(book);

        // Check if the book is in the database
        assertEquals(true, db.containsBook(book));

        // Attempt to add the same book to the database
        db.addBook(book);

        // Attemp to add a bogus clone to the database
        Book book2 = new Book("Where the Wild Things Are", "Fake Author");
        db.addBook(book2);
    }

    /**
     * Remove a book from the database
     */
    public void testDeleteBook() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Instatiate a book
        Book book = new Book("Charlie and the Chocolate Factory", "Roald Dahl");

        // Add it to the database
        db.addBook(book);

        // Remove book from the database
        db.deleteBook(book);

        // Try to get the book
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));
    }

    /**
     * Get a book from the database
     */
    public void testGetBook() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Head First Java", "Bert Bates and Kathy Sierra");
        book.setImageId(R.mipmap.ic_generic_cover);
        book.setReadStatus(2);
        book.setIsFavourite(true);
        book.setRating(5);

        // Attempt to add a book
        db.addBook(book);

        // Attempt to get a book
        Book fetchedBook = db.getBook(book.getId());

        // Display the book
        Log.i(TAG_DB_MANAGER, "Retrieved Book: " + fetchedBook.toString());
    }

    /**
     * Get a list of Books from the database
     */
    public void testGetBooks() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Instantiate three books
        Book book1 = new Book("Divergent", "Veronica Roth");
        Book book2 = new Book("Insurgent", "Veronica Roth");
        Book book3 = new Book("Allegiant", "Veronica Roth");

        // Add them to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);

        // Attempt to get the books from the database
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
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Instantiate three books
        Book book1  = new Book("The Hunger Games", "Suzanne Collins");
        Book book2  = new Book("Catching Fire", "Suzanne Collins");
        Book book3  = new Book("Mockingjay", "Suzanne Collins");

        // Add them to the database
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

        // Display the books
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook1.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook2.toString());
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + fetchedBook3.toString());
    }

    public void testSize() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Check the size (should be 0)
        assertEquals(0, db.size());

        // Instantiate three books
        Book book1  = new Book("What's the Worst That Could Happen", "Greg Craven");
        Book book2  = new Book("Writing With Style", "John Trimble");
        Book book3  = new Book("Procedural Programming in C++", "James Helfrich");

        // Add them to the database
        db.addBook(book1);
        db.addBook(book2);
        db.addBook(book3);

        // Check the size (should be 3)
        assertEquals(3, db.size());

        // Create books to go into the database
        Book book4 = new Book("The Lord of the Rings: The Fellowship of the Ring", "J.R.R. Tolkein");
        Book book5 = new Book("The Lord of the Rings: The Two Towers", "J.R.R. Tolkein");
        Book book6 = new Book("The Lord of the Rings: The Return of the King", "J.R.R. Tolkein");

        // Add the books to the database
        db.addBook(book4);
        db.addBook(book5);
        db.addBook(book6);

        // Check the size (should be 6)
        assertEquals(6, db.size());
    }

    /**
     * Update a book in the database
     */
    public void testUpdateBook() {
        // Instantiate the database
        DBManager db = new DBManager(getContext());
        db.onCreate(db.getWritableDatabase());

        // Create a book
        Book book = new Book("Ender's Game", "Orson Scott Card");

        // Add the book
        db.addBook(book);

        // Update a book
        book.setTitle("Ender's Shadow");
        db.updateBook(book);

        // Get the book again
        Log.i(TAG_DB_MANAGER, "Retrieved book:   " + db.getBook(book.getId()));
    }
}
