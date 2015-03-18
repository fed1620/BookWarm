package edu.byui.cs246.bookwarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * An SQLite Database to store the user's books and notes
 *
 * Sources referenced: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class DBManager extends SQLiteOpenHelper {
    private static final String   TAG_DB_MANAGER   = "DBManager";  // Log Tag
    private static final int      DATABASE_VERSION = 1;            // The database version
    private static final String   DATABASE_NAME    = "LibraryDB";  // The name of the database
    private static final String   TABLE_BOOKS      = "books";      // The name of the books table
    private static final String   KEY_ID           = "id";         // BOOKS table column 1
    private static final String   KEY_TITLE        = "title";      // BOOKS table column 2
    private static final String   KEY_AUTHOR       = "author";     // BOOKS table column 3

    // Default constructor
    public DBManager(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log message
        Log.i(TAG_DB_MANAGER, "Creating SQLite Database: " + DATABASE_NAME + "...");

        // Build an SQL statement that will be used to create the book table
        String CREATE_BOOK_TABLE =
                "CREATE TABLE books (" +
                "  id     INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", title  TEXT " +
                ", author TEXT)";

        // Drop the table before we create it
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BOOKS + ';';
        Log.i(TAG_DB_MANAGER, DROP_TABLE);
        db.execSQL(DROP_TABLE);

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
        // Make sure the book is not null
        if (book == null) {
            Log.e(TAG_DB_MANAGER, "Attempted to add null-referenced book");
            return;
        }

        // For logging, so we can se the results later when we run the app
        Log.i(TAG_DB_MANAGER, "Adding [Book " + book.getId() + "]: " +  book.toString());

        // 1. Get the reference to our database
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle());       // Title
        values.put(KEY_AUTHOR, book.getAuthor());     // Author

        // 3. Insert into the table
        db.insert(TABLE_BOOKS, null, values);

        // 4. Close the database
        db.close();

        // Display a success message
        // Log.i(TAG_DB_MANAGER, "Successfully added [Book " + book.getId() + "] to database: " + DATABASE_NAME);
    }

    /**
     * Retrieve a Book from the database, and return it as a Book object
     *
     * @param title The title of the book
     * @return Returns the book
     */
    public Book getBook(String title){
        // 1. Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // The query
        final String QUERY = "SELECT * FROM " + TABLE_BOOKS + " WHERE " + KEY_TITLE + " = \'" + title + "\';";

        // Print the query
        //Log.i(TAG_DB_MANAGER, QUERY);

        // 2. Build the cursor
        Cursor cursor = db.rawQuery(QUERY, null);

        // 3. If we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();      // Returns false if the cursor is empty
        } else {
            Log.i(TAG_DB_MANAGER, "ERROR: Cursor is null!");
        }

        // 4. build book object
        Book book = new Book();

        if (cursor != null && cursor.moveToFirst()) {
            Log.i(TAG_DB_MANAGER, "---------------------------------------------------------------");
            Log.i(TAG_DB_MANAGER, "ID:     " + cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            Log.i(TAG_DB_MANAGER, "TITLE:  " + cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            Log.i(TAG_DB_MANAGER, "AUTHOR: " + cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
            Log.i(TAG_DB_MANAGER, "---------------------------------------------------------------");

            book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            book.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));

            // Free the cursor
            cursor.close();
        } else {
            Log.i(TAG_DB_MANAGER, "ERROR: moveToFirst() returned FALSE");
        }

        // Close the database
        db.close();

        //log
        Log.i(TAG_DB_MANAGER, "Getting [Book " + book.getId() + "]: " + book.toString());

        // 5. return book
        return book;
    }
}
