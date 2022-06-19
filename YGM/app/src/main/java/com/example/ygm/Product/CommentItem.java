package com.example.ygm.Product;

public class CommentItem {

    private String name;
    private String level;
    private String date;
    private String content;

    public CommentItem(String name, String level, String date, String content) {
        this.name = name;
        this.level = level;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
