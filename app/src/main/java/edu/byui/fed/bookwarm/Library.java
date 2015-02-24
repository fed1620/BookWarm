package edu.byui.fed.bookwarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fed on 2/24/15.
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

    public List<Book> getBooks() {
        if (books.isEmpty()) {
            System.out.println("ERROR: Library contains no books");
        }
        return this.books;
    }
}
