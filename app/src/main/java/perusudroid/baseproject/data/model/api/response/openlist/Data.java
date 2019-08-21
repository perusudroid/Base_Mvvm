package perusudroid.baseproject.data.model.api.response.openlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Awesome Pojo Generator
 */
@Entity(tableName = "data")
public class Data {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "img_url")
    private String img_url;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "project_url")
    private String project_url;
    @ColumnInfo(name = "title")
    private String title;

    public Data() {
    }

    public Data(String img_url, String author, String description, String project_url, String title) {
        this.img_url = img_url;
        this.author = author;
        this.description = description;
        this.project_url = project_url;
        this.title = title;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setProject_url(String project_url) {
        this.project_url = project_url;
    }

    public String getProject_url() {
        return project_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}