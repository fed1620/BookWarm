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

    // Books table
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

    // Notes table
    private static final String   TABLE_NOTES  = "notes";             // The name of the NOTES table
    private static final String   KEY_NOTE_ID  = "id";                // NOTES table column 1
    private static final String   KEY_BOOK_ID  = "book_id";           // NOTES table column 2
    private static final String   KEY_PAGE     = "page_number";       // NOTES table column 3
    private static final String   KEY_CONTENT  = "content";           // NOTES table column 4
    private static final String[] COLUMNS_NOTE = {KEY_NOTE_ID,        // Every column in NOTES
                                                  KEY_BOOK_ID,
                                                  KEY_PAGE,
                                                  KEY_CONTENT};

    // Default constructor
    public DBManager(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Build an SQL statement that will be used to create the book table
        String CREATE_BOOK_TABLE =
                "CREATE TABLE books (" +
                "  id          INTEGER PRIMARY KEY" +
                ", title       TEXT" +
                ", author      TEXT" +
                ", image_id    INTEGER" +
                ", read_status INTEGER" +
                ", favorite    INTEGER" +
                ", rating      INTEGER" +
                ", date        INTEGER);";

        // Build an SQL statement that will be used to create the Notes table
        String CREATE_NOTES_TABLE =
                "CREATE TABLE notes (" +
                        "  id          INTEGER PRIMARY KEY" +
                        ", book_id     INTEGER" +
                        ", page_number INTEGER" +
                        ", content     TEXT);";

        // Log messages
        Log.i(TAG_DB_MANAGER, "Creating table: " + TABLE_BOOKS + "...");
        Log.i(TAG_DB_MANAGER, "Creating table: " + TABLE_NOTES + "...");

        // Run the statements
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the outdated book table if it already exists
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS notes");

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

        // Create ContentValues and add the different column values
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

        // If the Book contains Notes, insert them into the Notes table
        if (book.getNotes().size() != 0) {
            for (Note note : book.getNotes()) {
                ContentValues valuesNote = new ContentValues();
                valuesNote.put(KEY_BOOK_ID, book.getId());                  // BookId
                valuesNote.put(KEY_PAGE,    note.getPageNumber());          // Page Number
                valuesNote.put(KEY_CONTENT, note.getNoteContent());         // Content

                // Insert the values into the note table
                long idNoteInsert = db.insert(TABLE_NOTES, null, valuesNote);

                // Set the Note's id and the Note's bookId
                note.setId((int)idNoteInsert);
                note.setBookId(book.getId());
            }
        }
    }

    /**
     * Retrieve a Book from the database, and return it as a Book object
     *
     * @param id The id of the book
     * @return Returns the book
     */
    public Book getBook(int id){
        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the book cursor
        Cursor cursor = db.query(TABLE_BOOKS,
                                 COLUMNS,
                                 " id = ?",
                                 new String[] {String.valueOf(id)},
                                 null,
                                 null,
                                 null,
                                 null);

        // Build the notes cursor
        Cursor noteCursor = db.query(TABLE_NOTES,
                                     COLUMNS_NOTE,
                                     " book_id = ?",
                                     new String[] {String.valueOf(id)},
                                     null,
                                     null,
                                     null,
                                     null);
        // Build a book object
        Book book = new Book();

        if (cursor != null && cursor.moveToFirst()) {
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
        } else if (cursor == null) {
            Log.i(TAG_DB_MANAGER, "ERROR: Cursor is null!");
        } else if (!cursor.moveToFirst()) {
            Log.i(TAG_DB_MANAGER, "ERROR: moveToFirst() returned FALSE");
        }

        // Check if the book has notes
        if (noteCursor != null && noteCursor.moveToFirst() && cursor != null && cursor.moveToFirst()) {
            if (noteCursor.getInt(noteCursor.getColumnIndex(KEY_BOOK_ID)) == cursor.getInt(cursor.getColumnIndex(KEY_ID))) {
                do {
                    Note note = new Note();
                    note.setId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_NOTE_ID)));
                    note.setBookId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_BOOK_ID)));
                    note.setPageNumber(noteCursor.getInt(noteCursor.getColumnIndex(KEY_PAGE)));
                    note.setNoteContent(noteCursor.getString(noteCursor.getColumnIndex(KEY_CONTENT)));
                    book.addNote(note);
                } while (noteCursor.moveToNext());
            }
            // Free the note cursor
            noteCursor.close();
        }
        // Free the book cursor
        if (cursor != null) {
            cursor.close();
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

                // Build the notes cursor
                Cursor noteCursor = db.query(TABLE_NOTES,
                                             COLUMNS_NOTE,
                                             " book_id = ?",
                                             new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_ID)))},
                                             null,
                                             null,
                                             null,
                                             null);

                // Get all of the notes that correspond to this book
                if (noteCursor != null && noteCursor.moveToFirst()) {
                    if (noteCursor.getInt(noteCursor.getColumnIndex(KEY_BOOK_ID)) == cursor.getInt(cursor.getColumnIndex(KEY_ID))) {
                        do {
                            Note note = new Note();
                            note.setId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_NOTE_ID)));
                            note.setBookId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_BOOK_ID)));
                            note.setPageNumber(noteCursor.getInt(noteCursor.getColumnIndex(KEY_PAGE)));
                            note.setNoteContent(noteCursor.getString(noteCursor.getColumnIndex(KEY_CONTENT)));
                            book.addNote(note);
                        } while (noteCursor.moveToNext());
                    }
                    // Free the note cursor
                    noteCursor.close();
                }

                books.add(book);
            } while (cursor.moveToNext());
        }
        // Free the book cursor
        cursor.close();

        return books;
    }

    /**
     * Retrieve a Note from the database, and return it as a Note object
     *
     * @param id The id of the note
     * @return Returns the note
     */
    public Note getNote(int id) {
        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the notes cursor
        Cursor noteCursor = db.query(TABLE_NOTES,
                COLUMNS_NOTE,
                " id = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);

        // Build a note object
        Note note = new Note();

        if (noteCursor != null && noteCursor.moveToFirst()) {
            note.setId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_NOTE_ID)));
            note.setBookId(noteCursor.getInt(noteCursor.getColumnIndex(KEY_BOOK_ID)));
            note.setPageNumber(noteCursor.getInt(noteCursor.getColumnIndex(KEY_PAGE)));
            note.setNoteContent(noteCursor.getString(noteCursor.getColumnIndex(KEY_CONTENT)));

            // Close the note cursor
            noteCursor.close();

        } else if (noteCursor == null) {
            Log.i(TAG_DB_MANAGER, "ERROR: Note cursor is null!");
        } else if (!noteCursor.moveToFirst()) {
            Log.i(TAG_DB_MANAGER, "ERROR: moveToFirst() returned FALSE");
        }

        // Log it
        Log.i(TAG_DB_MANAGER, "Returned [Note " + note.getId() + "] corresponding to [Book " +
                note.getBookId() + "]");

        // Return the book object
        return note;
    }

    /**
     * Update a Book
     * @param book The up-to-date Book object
     */
    public void updateBook(Book book) {
        // Get reference to the writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Put the book information into a ContentValues
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,    book.getTitle());          // Title
        values.put(KEY_AUTHOR,   book.getAuthor());         // Author
        values.put(KEY_IMAGE,    book.getImageId());        // ImageId
        values.put(KEY_STATUS,   book.getReadStatus());     // Read Status
        values.put(KEY_FAVORITE, book.getIsFavourite());    // Favorite status
        values.put(KEY_RATING,   book.getRating());         // Rating
        values.put(KEY_DATE,     book.getDatePublished());  // Author

            // Only insert the notes that aren't already in the database
            for (Note note : book.getNotes()) {
                if (!containsNote(note)) {
                    ContentValues valuesNote = new ContentValues();
                    valuesNote.put(KEY_BOOK_ID, book.getId());                  // BookId
                    valuesNote.put(KEY_PAGE,    note.getPageNumber());          // Page Number
                    valuesNote.put(KEY_CONTENT, note.getNoteContent());         // Content

                    // Insert the values into the note table
                    long idNoteInsert = db.insert(TABLE_NOTES, null, valuesNote);

                    // Set the Note's id and the Note's bookId
                    note.setId((int)idNoteInsert);
                    note.setBookId(book.getId());

                    Log.i(TAG_DB_MANAGER, "Inserted new note for Book " + book.getId() + " with an ID of " + note.getId());
                }
            }

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

        // Delete any notes associated with the book
        db.delete(TABLE_NOTES, KEY_BOOK_ID + " = ?", new String[] {String.valueOf(book.getId())});

        // Log
        Log.i(TAG_DB_MANAGER, "Removed book: " + book.toString());
    }

    /**
     * Update a Note
     * @param note The up-to-date Note object
     */
    public void updateNote(Note note) {
        // Get reference to the writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // The page number and content will be updated, but
        // the ID of the note and the Book ID should be the same
        ContentValues values = new ContentValues();
        values.put(KEY_PAGE,    note.getPageNumber());          // Page Number
        values.put(KEY_CONTENT, note.getNoteContent());         // Content

        // Update the row
        db.update(TABLE_NOTES, values, KEY_ID+" = ?", new String[] {String.valueOf(note.getId())});

        // Log message
        Log.i(TAG_DB_MANAGER, "Updated Note: " + note.toString());
    }

    /**
     * Remove a note from the database
     * @param note The note to be deleted
     */
    public void deleteNote(Note note) {
        // Get the reference to the writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete a specific note
        db.delete(TABLE_NOTES, KEY_NOTE_ID + " = ?", new String[] {String.valueOf(note.getId())});

        // Log
        Log.i(TAG_DB_MANAGER, "Removed note: " + note.getId());
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
     * Return true if a note is contained in the database (based on the id)
     * @param note This note is either in the database, or it isn't
     * @return Returns true if the note is in the database
     */
    public boolean containsNote(Note note) {
        // By default, a note is not in the database
        boolean isInDB = false;

        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build the cursor
        Cursor cursor = db.query(TABLE_NOTES,
                                 COLUMNS_NOTE,
                                 " id = ?",
                                 new String[] {String.valueOf(note.getId())},
                                 null,
                                 null,
                                 null,
                                 null);

        // If the cursor moves to the correct row, then the note is in the database
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
        final String DROP_TABLE_BOOKS = "DROP TABLE IF EXISTS " + TABLE_BOOKS + ';';
        Log.i(TAG_DB_MANAGER, "Dropping table: " + TABLE_BOOKS + "...");
        db.execSQL(DROP_TABLE_BOOKS);

        // Drop the notes table
        final String DROP_TABLE_NOTES = "DROP TABLE IF EXISTS " + TABLE_NOTES + ';';
        Log.i(TAG_DB_MANAGER, "Dropping table: " + TABLE_NOTES + "...");
        db.execSQL(DROP_TABLE_NOTES);

        // Recreate it
        this.onCreate(db);
    }
}
