package edu.byui.cs246.bookwarm;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Megiddo on 2/26/2015.
 */
public class Note {
    private String noteContent;
    private Date dateAuthored;
    private int pageNumber;

    public String getNoteContent() {
        return noteContent;
    }

    public Date getDateAuthored() {
        return dateAuthored;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setNoteContent(String noteContentTemp) {
        noteContent = noteContentTemp;
    }
    public void setDateAuthored(Date dateAuthoredTemp) {
        dateAuthored = dateAuthoredTemp;
    }
    public void setPageNumber(int pageNumberTemp) {
        pageNumber = pageNumberTemp;
    }

    //todo: implement loading functionality for these stubs
    public void loadNoteFromFile() throws IOException {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("note")));
            String inputString;
            List<String> items = new ArrayList<>();
            while ((inputString = inputReader.readLine()) != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveNoteToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("note");

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
