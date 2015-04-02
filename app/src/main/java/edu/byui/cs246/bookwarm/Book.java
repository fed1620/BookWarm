package edu.byui.cs246.bookwarm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Book consists of a title, author, image id, read status, favorite status, rating, and
 * publish date
 */
public class Book implements Serializable {
    private int        id;                     // Primary key
    private String     title;
    private String     author;
    private Integer    imageId;
    private int        readStatus;             // By default, a book has not yet been read
    private boolean    isFavourite;            // By default, a book is not favorited
    private int        rating;                 // By default, a book is not rated
    private int        datePublished;
    private ArrayList<Note> notes;

    // Default Constructor
    public Book() {
        this.title         = null;
        this.author        = null;
        this.imageId       = null;
        this.readStatus    = 0;
        this.isFavourite   = false;
        this.rating        = 0;
        this.datePublished = 0;
        this.notes = new ArrayList<>();
    }

    // Non-Default constructor
    public Book(String title, String author) {
        this.title         = title;
        this.author        = author;
        this.imageId       = null;
        this.readStatus    = 0;
        this.isFavourite   = false;
        this.rating        = 0;
        this.datePublished = 0;
        this.notes = new ArrayList<>();
    }

    // Setters
    public void setId(int id)                       {this.id = id;}
    public void setTitle(String title)              {this.title = title;}
    public void setAuthor(String author)            {this.author = author;}
    public void setImageId(Integer imageId)         {this.imageId = imageId;}
    public void setReadStatus(int readStatus)       {this.readStatus = readStatus;}
    public void setIsFavourite(boolean isFavourite) {this.isFavourite = isFavourite; }
    public void setRating(int rating)               {this.rating = rating;}
    public void setDatePublished(int datePublished) {this.datePublished = datePublished;}

    // Getters
    public int     getId()            {return this.id;}
    public String  getTitle()         {return title;}
    public String  getAuthor()        {return author;}
    public Integer getImageId()       {return imageId;}
    public int     getReadStatus()    {return readStatus;}
    public boolean getIsFavourite()   {return isFavourite;}
    public int     getRating()        {return rating;}
    public int     getDatePublished() {return datePublished;}

    // Notes
    public void       addNote(Note note)    {this.notes.add(note);}
    public void       removeNote(int index) {this.notes.remove(index);}
    public ArrayList<Note> getNotes()            {return notes;}

    /**
     * Display a book
     * @return Returns a string that display the title and author
     */
    @Override
    public String toString() {return '"' + getTitle() + "\" by "  + getAuthor();}
}
