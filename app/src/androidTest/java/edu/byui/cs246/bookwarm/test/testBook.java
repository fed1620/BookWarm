package edu.byui.cs246.bookwarm.test;

import junit.framework.TestCase;

import edu.byui.cs246.bookwarm.Book;

/**
 * Test the book class
 */
public class testBook extends TestCase{

    //check the title
    public void testTitleStuff() {
        Book dummyBook = new Book();
        dummyBook.setTitle("Herp de Derp");

        assertEquals("Herp de Derp", dummyBook.getTitle());
    }

    //void test with the book title
    public void testBreakTheTitle () {
        Book dummyBook = new Book();

        assertEquals(null, dummyBook.getTitle());
    }
}