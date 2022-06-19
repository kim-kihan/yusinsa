package com.example.ygm.Home;

public class HomeRankingGridItem {

    private String pk;
    private int rank;
    private String img;
    private String name;
    private int price;

    public HomeRankingGridItem(String pk, String img, String name, int price) {
        this.pk = pk;
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public String getPK() {
        return pk;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
}
