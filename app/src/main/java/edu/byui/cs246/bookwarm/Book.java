package edu.byui.cs246.bookwarm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Book now stores a title and an image ID
 */
public class Book implements Serializable {
    private int        id;                         // To store in our SQLite database
    private String     title;
    private String     author;
    private Integer    imageId;
    private int        readStatus = 0;             // By default, a book has not yet been read
    private boolean    isFavourite = false;        // By default, a book is not favorited
    private int        rating = 0;                 // By default, a book is not rated
    private int        datePublished;
    private List<Note> notes;

    public Book() {
        notes = new ArrayList<>();
    }

    // Setters and Getters
    public void setId(int id) { this.id = id; }
    public int  getId()       { return this.id; }

    public void setTitle(String title) {
        // Do nothing with an invalid title
        if (title == null || title.isEmpty()) {
            return;
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImageId() { return imageId; }

    public String getAuthor() { return author; }

    public int getReadStatus() { return readStatus; }

    public boolean getIsFavourite() { return isFavourite; }

    public int getRating() { return rating; }

    public int getDatePublished() { return datePublished; }

    public List<Note> getNotes() { return notes; }

    public void setAuthor(String author) { this.author = author; }

    public void setReadStatus(int readStatus) { this.readStatus = readStatus; }

    public void setIsFavourite(boolean isFavourite) { this.isFavourite = isFavourite; }

    public void setRating(int rating) { this.rating = rating; }

    public void setDatePublished(int datePublished) { this.datePublished = datePublished; }

    public void addNote(Note note) { this.notes.add(note); }

    public void removeNote(int index) { this.notes.remove(index); }

    @Override
    public String toString() {
        return '"' + getTitle() + "\" by "  + getAuthor();
    }
}
