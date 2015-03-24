package edu.byui.cs246.bookwarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * An SQLite Database to store the user's books and notes
 *
 * Sources referenced: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class DBManager extends SQLiteOpenHelper {
    private static final int      DATABASE_VERSION = 1;               // The database version
    private static final String   TAG_DB_MANAGER   = "DBManager";     // Log Tag
    private static final String   DATABASE_NAME    = "LibraryDB";     // The name of the database
    private static final String   TABLE_BOOKS      = "books";         // The name of the BOOKS table
    private static final String   KEY_ID           = "id";            // BOOKS table column 1
    private static final String   KEY_TITLE        = "title";         // BOOKS table column 2
    private static final String   KEY_AUTHOR       = "author";        // BOOKS table column 3
    private static final String   KEY_IMAGE        = "image_id";      // BOOKS table column 4
    private static final String   KEY_STATUS       = "read_status";   // BOOKS table column 5
    private static final String   KEY_FAVORITE     = "favorite";      // BOOKS table column 6
    private static final String   KEY_RATING       = "rating";        // BOOKS table column 7
    private static final String   KEY_DATE         = "date";          // BOOKS table column 8
    private static final String[] COLUMNS = {KEY_ID,                  // Every column in BOOKS
                                             KEY_TITLE,
                                             KEY_AUTHOR,
                                             KEY_IMAGE,
                                             KEY_STATUS,
                                             KEY_FAVORITE,
                                             KEY_RATING,
                                             KEY_DATE};

    // Default constructor
    public DBManager(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Build an SQL statement that will be used to create the book table
        String CREATE_BOOK_TABLE =
                "CREATE TABLE books (" +
                "  id          INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", title       TEXT" +
                ", author      TEXT" +
                ", image_id    INTEGER" +
                ", read_status INTEGER" +
                ", favorite    INTEGER" +
                ", rating      INTEGER" +
                ", date        INTEGER);";

        // Log message
        Log.i(TAG_DB_MANAGER, "Creating table: " + TABLE_BOOKS + "...");

        // Run the statement
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the outdated book table if it already exists
        db.execSQL("DROP TABLE IF EXISTS books");

        // Create the new book table
        this.onCreate(db);
    }

    /**
     * Add a book to the table: "books"
     * @param book The book to be added
     */
    public void addBook(Book book) {
        // Get the reference to our database
        SQLiteDatabase db = this.getWritableDatabase();

        // Make sure the book is not null
        if (book == null) {
            Log.e(TAG_DB_MANAGER, "Attempted to add null-referenced book");
            return;
        }

        // Make sure the book isn't already in the database
        if (containsBook(book)) {
            Log.e(TAG_DB_MANAGER, "Database already contains book: " + book.getTitle());
            return;
        }

        // 2. Create ContentValues and add the different column values
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,    book.getTitle());          // Title
        values.put(KEY_AUTHOR,   book.getAuthor());         // Author
        values.put(KEY_IMAGE,    book.getImageId());        // ImageId
        values.put(KEY_STATUS,   book.getReadStatus());     // Read Status
        values.put(KEY_FAVORITE, book.getIsFavourite());    // Favorite status
        values.put(KEY_RATING,   book.getRating());         // Rating
        values.put(KEY_DATE,     book.getDatePublished());  // Author

        // Insert the values into the table
        long idInsert = db.insert(TABLE_BOOKS, null, values);

        // Set the ID of the book
        book.setId((int)idInsert);
    }

    /**
     * Retrieve a Book from the database, and return it as a Book object
     *
     * @param id The id of the book
     * @return Returns the book
     */
    public Book getBook(int id){
        // 1. Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the cursor
        Cursor cursor = db.query(TABLE_BOOKS,
                                 COLUMNS,
                                 " id = ?",
                                 new String[] {String.valueOf(id)},
                                 null,
                                 null,
                                 null,
                                 null);
        // Build a book object
        Book book = new Book();

        if (cursor != null && cursor.moveToFirst()) {
            Log.i(TAG_DB_MANAGER, "_______________________________________________________________");
            Log.i(TAG_DB_MANAGER, "ID:        " + cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            Log.i(TAG_DB_MANAGER, "TITLE:     " + cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            Log.i(TAG_DB_MANAGER, "AUTHOR:    " + cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
            Log.i(TAG_DB_MANAGER, "IMAGE:     " + cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)));
            Log.i(TAG_DB_MANAGER, "STATUS:    " + cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));
            Log.i(TAG_DB_MANAGER, "FAVORITE:  " + cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE)));
            Log.i(TAG_DB_MANAGER, "RATING:    " + cursor.getInt(cursor.getColumnIndex(KEY_RATING)));
            Log.i(TAG_DB_MANAGER, "DATE:      " + cursor.getInt(cursor.getColumnIndex(KEY_DATE)));
            Log.i(TAG_DB_MANAGER, "---------------------------------------------------------------");

            book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            book.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
            book.setImageId(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)));
            book.setReadStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));
            book.setRating(cursor.getInt(cursor.getColumnIndex(KEY_RATING)));
            book.setDatePublished(cursor.getInt(cursor.getColumnIndex(KEY_DATE)));

            // Convert the integer back to a boolean value
            if (cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE)) == 0) {
                book.setIsFavourite(false);
            } else {
                book.setIsFavourite(true);
            }

            // Free the cursor
            cursor.close();
        } else if (cursor == null) {
            Log.i(TAG_DB_MANAGER, "ERROR: Cursor is null!");
        } else if (!cursor.moveToFirst()) {
            Log.i(TAG_DB_MANAGER, "ERROR: moveToFirst() returned FALSE");
        }

        // Return the book object
        return book;
    }

    /**
     * Return all books in the database
     * @return Returns a List of Book objects
     */
    public List<Book> getBooks() {
        // The list of books in the database
        List<Book> books = new ArrayList<>();

        // Get the reference to the writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Build the query
        final String QUERY = "SELECT * FROM " + TABLE_BOOKS + ';';

        // Build the cursor
        Cursor cursor = db.rawQuery(QUERY, null);

        // For every row in the table, build a book object and add it to the list
        Book book;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
                book.setImageId(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)));
                book.setReadStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));
                book.setRating(cursor.getInt(cursor.getColumnIndex(KEY_RATING)));
                book.setDatePublished(cursor.getInt(cursor.getColumnIndex(KEY_DATE)));

                // Convert the integer back to a boolean value
                if (cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE)) == 0) {
                    book.setIsFavourite(false);
                } else {
                    book.setIsFavourite(true);
                }

                books.add(book);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return books;
    }

    /**
     * Update a Book
     * @param book The up-to-date Book object
     */
    public void updateBook(Book book) {
        // Get reference to the writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Put the new information into a ContentValues
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,    book.getTitle());          // Title
        values.put(KEY_AUTHOR,   book.getAuthor());         // Author
        values.put(KEY_IMAGE,    book.getImageId());        // ImageId
        values.put(KEY_STATUS,   book.getReadStatus());     // Read Status
        values.put(KEY_FAVORITE, book.getIsFavourite());    // Favorite status
        values.put(KEY_RATING,   book.getRating());         // Rating
        values.put(KEY_DATE,     book.getDatePublished());  // Author

        // Update the row
        db.update(TABLE_BOOKS, values, KEY_ID+" = ?", new String[] {String.valueOf(book.getId())});

        // Log message
        Log.i(TAG_DB_MANAGER, "Updated book: " + book.toString());

    }

    /**
     * Remove a book from the database
     * @param book The book to be deleted
     */
    public void deleteBook(Book book) {
        // Get the reference to the writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the book
        db.delete(TABLE_BOOKS, KEY_ID + " = ?", new String[] {String.valueOf(book.getId())});

        // Log
        Log.i(TAG_DB_MANAGER, "Removed book: " + book.toString());
    }

    /**
     * How many books are in our database?
     * @return Returns the number of books
     */
    public int size() {
        // The integer we will be returning
        int i = 0;

        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the query
        final String QUERY = "SELECT * FROM " + TABLE_BOOKS + ';';

        // Build the cursor
        Cursor cursor = db.rawQuery(QUERY, null);

        // Count every row in the table
        if (cursor.moveToFirst()) {
            do {
                ++i;
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return i;
    }

    /**
     * Return true if a book is contained in the database (based on the title)
     * @param book This book is either in the database, or it isn't
     * @return Returns true if the book is in the database
     */
    public boolean containsBook(Book book) {
        // By default, a book is not in the database
        boolean isInDB = false;

        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the cursor
        Cursor cursor = db.query(TABLE_BOOKS,
                                 COLUMNS,
                                 " title = ?",
                                 new String[] {book.getTitle()},
                                 null,
                                 null,
                                 null,
                                 null);

        // If the cursor moves to the correct row, then the book is in the database
        if (cursor != null && cursor.moveToFirst()) {
            isInDB = true;

            // Free the cursor
            cursor.close();
        }

        return isInDB;
    }

    /**
     * Clear the database
     */
    public void clear() {
        // If the database is empty, do nothing
        if (size() == 0) {
            return;
        }

        // Get the reference to this database
        SQLiteDatabase db = this.getWritableDatabase();

        // Drop the books table
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BOOKS + ';';
        Log.i(TAG_DB_MANAGER, "Dropping table: " + TABLE_BOOKS + "...");
        db.execSQL(DROP_TABLE);

        // Recreate it
        this.onCreate(db);
    }
}
