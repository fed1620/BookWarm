package edu.byui.cs246.bookwarm;

import java.io.Serializable;

/**
 * A note consists of content, and optionally, a page number referring
 * to the corresponding page in the book
 */
public class Note implements Serializable {
    private int     id;                                // Primary key
    private int     bookId;                            // Which book does the note map to?
    private String  noteContent;
    private int     pageNumber;

    // Default constructor
    public Note() {}

    // Non-default constructor
    public Note(String noteContent) {
        this.noteContent = noteContent;
    }

    // Non-default constructor
    public Note(Integer pageNumber, String noteContent) {
        this.pageNumber  = pageNumber;
        this.noteContent = noteContent;
    }

    // Setters
    public void setId(int id)                          {this.id = id;}
    public void setBookId(int bookId)                  {this.bookId = bookId;}
    public void setNoteContent(String noteContentTemp) {noteContent = noteContentTemp;}
    public void setPageNumber(int pageNumberTemp)      {pageNumber = pageNumberTemp;}

    // Getters
    public int    getId()             {return this.id;}
    public int    getBookId()         {return this.bookId;}
    public String getNoteContent()    {return noteContent;}
    public int    getPageNumber()     {return pageNumber;}
}