package com.example.ygm.Product;

public class ReviewItem {

    private String PID;
    private String RID;
    private String level;
    private String name;  //id
    private String date;
    private String title; //pname
    private String size;
    private String color;
    private String height;
    private String weight;
    private String content;
    private String resId, reviewId, ratingnum;

    public ReviewItem(String PID, String RID, String level, String name, String date, String title, String size, String color, String height, String weight, String content, String resId, String ratingnum, String reviewId) {
        this.RID = RID;
        this.PID = PID;
        this.content = content;
        this.date = date;
        this.level = level;
        this.title = title;
        this.height = height;
        this.weight = weight;
        this.name = name;
        this.ratingnum = ratingnum;
        this.resId = resId;
        this.reviewId = reviewId;
        this.size = size;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getRID() {
        return RID;
    }

    public String getPID() {
        return PID;
    }

    public void setRatingnum(String ratingnum) {
        this.ratingnum = ratingnum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getResId() {
        return resId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getRatingnum() {
        return ratingnum;
    }
}

