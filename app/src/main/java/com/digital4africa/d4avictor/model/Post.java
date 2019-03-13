package com.digital4africa.d4avictor.model;

public class Post {
    private String title;

    private String excerpt;
    private String date;
    private String content;

    private String thumbnail;

    private String author;

    public Post(String title, String excerpt,
                String date, String content, String thumbnail, String author) {
        this.title = title;
        this.excerpt = excerpt;
        this.date = date;
        this.content = content;
        this.thumbnail = thumbnail;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAuthor() {
        return author;
    }
}
