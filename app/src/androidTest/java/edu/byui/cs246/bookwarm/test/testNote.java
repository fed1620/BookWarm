package edu.byui.cs246.bookwarm.test;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.byui.cs246.bookwarm.Note;

/**
 * Test Set Number Page and Load Note From File functions
 */
public class testNote extends TestCase {

    // test set page number
    public void testSetPageNumber() {
        Note test = new Note();
        test.setPageNumber(1);

        assertEquals(1, test.getPageNumber());
    }

    // fail read function
    public void testLoadNoteFromFile() throws IOException {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("note")));
            String inputString = inputReader.readLine();
            assertEquals(null, inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
