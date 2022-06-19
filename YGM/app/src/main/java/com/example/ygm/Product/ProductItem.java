package com.example.ygm.Product;

public class ProductItem {

    String PID;
    String thumbnail;
    String title1, title2, title3;
    String title;
    String price;
    String content;
    String main;
    String star;

    public ProductItem(String PID, String thumbnail, String title1, String title2, String title3, String content, String price, String main, String star) {
        this.PID = PID;
        this.thumbnail = thumbnail;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.content = content;
        this.main = main;
        this.price = price;
        this.star = star;

    }

    public String getPID() {
        return PID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getStar() {
        return star;
    }

    public String getMain() {
        return main;
    }

    public String getTitle1() {
        return title1;
    }

    public String getTitle2() {
        return title2;
    }

    public String getTitle3() {
        return title3;
    }
}
