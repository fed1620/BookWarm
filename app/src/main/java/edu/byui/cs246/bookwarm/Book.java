package edu.byui.cs246.bookwarm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Book now stores a title and an image ID
 */
public class Book implements Serializable{
    private String title;
    private Integer cover;
    private String author;
    private String publisher;
    private int readStatus;
    private boolean isFavourite;
    private int customRating;
    private int releaseDate;
    private List<Note> notes;

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

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public Integer getImageId() { return cover; }

    public int getCover() { return cover; }

    public String getAuthor() { return author; }

    public String getPublisher() { return publisher; }

    public int getReadStatus() { return readStatus; }

    public boolean getIsFavourite() { return isFavourite; }

    public int getCustomRating() { return customRating; }

    public int getReleaseDate() { return releaseDate; }

    public List<Note> getNotes() { return notes; }

    public void setCover(int cover) { this.cover = cover; }

    public void setAuthor(String author) { this.author = author; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public void setReadStatus(int readStatus) { this.readStatus = readStatus; }

    public void setIsFavourite(boolean isFavourite) { this.isFavourite = isFavourite; }

    public void setIsCustomRating(int customRating) { this.customRating = customRating; }

    public void setReleaseDate(int releaseDate) { this.releaseDate = releaseDate; }

    public void addNote(List<Note> notes) { this.notes = notes; }

    public void removeNote(List<Note> notes) { this.notes = notes; }

    public void loadFromFile() {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("book")));
            String inputString;
            List<String> items = new ArrayList<>();
            while ((inputString = inputReader.readLine()) != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("book");

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
