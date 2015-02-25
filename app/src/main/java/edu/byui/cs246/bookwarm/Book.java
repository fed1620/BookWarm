package edu.byui.cs246.bookwarm;

/**
 * A Book now stores a title and an image ID
 */
public class Book {
    private String title;
    private Integer imageId;

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
}
