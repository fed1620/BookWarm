package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.bookwarm.Book;
import edu.byui.cs246.bookwarm.Library;

/**
 * Tests the Library class
 */
public class testLibrary extends AndroidTestCase {

    /**
     * Test the following methods:
     *      addBook()
     *      deleteBook()
     *      numBooks()
     */
    public void test1() {
        // The library we will be using for this test
        Library library = Library.getInstance();

        // Library should not contain any books yet...
        assertEquals(0, library.numBooks());

        // Create a book to add to the library
        Book book1 = new Book("Where the Red Fern Grows","Wilson Rawls");
        library.addBook(book1);

        // Library should now contain 1 book
        assertEquals(1, library.numBooks());

        // Add another book to the library
        Book book2 = new Book("The Giver", "Lois Lowry");
        library.addBook(book2);

        // Library should now contain 2 books
        assertEquals(2, library.numBooks());

        // Remove the first book
        library.deleteBook(book1);

        // Library should now contain 1 book
        assertEquals(1, library.numBooks());

        // Remove the last book
        library.deleteBook(book2);

        // Library should now contain 0 books
        assertEquals(0, library.numBooks());
    }

    /**
     * Test the following methods:
     *      getBooks()
     *      getBookTitles()
     *      getBookImageIds()
     */
    public void test2() {
        // The library we will be using for this test
        Library library = Library.getInstance();       // This is the same library from test1()
        library.display();                             // There should not be any books

        // Create four different books
        Book book1 = new Book("Where the Red Fern Grows","Wilson Rawls");
        Book book2 = new Book("Catcher in the Rye", "J.D. Salinger");
        Book book3 = new Book("The Giver", "Lois Lowry");
        Book book4 = new Book("Head First Java", "Bert Bates and Kathy Sierra");

        // Insert each these books into a list of Books, AND into
        // our library
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.display();

        // We expect getBooks() to return the same list of Books that
        // we ourselves created
        assertEquals(bookList, library.getBooks());

        // Create arrays for both the book titles and their image IDs
        Integer[] imageIDs = new Integer[bookList.size()];
        String[] titles = new String[bookList.size()];

        // Fill the arrays
        for (int i = 0; i < bookList.size(); i++) {
            titles[i] = bookList.get(i).getTitle();
            imageIDs[i] = bookList.get(i).getImageId();
        }

        // Ensure that the contents in the local array matches the
        // contents of the array that is given to us by the library
        for (int i = 0; i < bookList.size(); i++) {
            assertEquals(titles[i], library.getBookTitles()[i]);
            assertEquals(imageIDs[i], library.getImageIds()[i]);
        }
    }
}
