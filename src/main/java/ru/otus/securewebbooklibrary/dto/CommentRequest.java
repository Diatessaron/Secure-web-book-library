package ru.otus.securewebbooklibrary.dto;

public class CommentRequest {
    private String content;
    private String book;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
