package org.example;

public class Document {
    String title;
    String author;
    String date;

    public Document(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

}