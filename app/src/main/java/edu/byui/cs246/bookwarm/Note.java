package edu.byui.cs246.bookwarm;

import java.util.Date;

/**
 * A note consists of content, the date on which it was written, and a page number referring
 * to the corresponding page in the book
 */
public class Note {
    private int id;                      // For the SQLite database
    private String noteContent;
    private Date dateAuthored;
    private int pageNumber;

    // Setters
    public void setId(int id)                          {this.id = id;}
    public void setNoteContent(String noteContentTemp) {noteContent = noteContentTemp;}
    public void setDateAuthored(Date dateAuthoredTemp) {dateAuthored = dateAuthoredTemp;}
    public void setPageNumber(int pageNumberTemp)      {pageNumber = pageNumberTemp;}

    // Getters
    public int getId()              {return this.id;}
    public String getNoteContent()  {return noteContent;}
    public Date   getDateAuthored() {return dateAuthored;}
    public int    getPageNumber()   {return pageNumber;}
}
