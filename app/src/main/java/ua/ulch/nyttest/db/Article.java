package ua.ulch.nyttest.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Article {
    @PrimaryKey
    private long id;
    private String title;
    private String publishedDate;
    private String url;
    private String image;
    private String html;

    public Article() {
    }
@Ignore
    public Article(long id
        , String title
        , String publishedDate
        , String url
        , String image
        , String html) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.url = url;
        this.image = image;
        this.html = html;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
