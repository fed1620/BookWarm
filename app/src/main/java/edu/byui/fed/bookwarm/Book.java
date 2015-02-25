package edu.byui.fed.bookwarm;

/**
 * Created by Fed on 2/24/15.
 * The first version of our book class will simply store a title
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
