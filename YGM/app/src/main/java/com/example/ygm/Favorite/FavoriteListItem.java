package com.example.ygm.Favorite;

public class FavoriteListItem {

    private String pk;
    private String img;
    private String name;
    private int price;
    private String num;

    public FavoriteListItem(String pk, String img, String name, int price, String num) {
        this.pk = pk;
        this.img = img;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getPK() {
        return pk;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }
}
