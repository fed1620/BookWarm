package edu.byui.cs246.bookwarm;

import java.util.ArrayList;
import java.util.List;

/**
 * A library is a list of books
 */
public class Library {
    private List<Book> books = new ArrayList<>();

    /**
     * Add a book object to the list of books
     * @param book The book to be added
     */
    public void addBook(Book book) {
        if (book == null) {
            System.out.println("ERROR: Cannot add a book with value: null");
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
        }
        return getBooks().size();
    }

    /**
     * A getter that will return our list of books
     *
     * @return Returns List<Book> books
     */
    public List<Book> getBooks() {
        if (books.isEmpty()) {
            System.out.println("ERROR: Library contains no books");
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
}
