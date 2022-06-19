package com.example.ygm.Home;

public class RankingListItem {

    private String pk;
    private String name;
    private String img;
    private int price;
    private int favorite;
    private float rate;
    private int review;
    private int cid;

    public RankingListItem(String pk, String name, String img, int price, int favorite, float rate, int review, int cid) {
        this.pk = pk;
        this.name = name;
        this.img = img;
        this.price = price;
        this.favorite = favorite;
        this.rate = rate;
        this.review = review;
        this.cid = cid;
    }

    public String getPK() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public int getFavorite() {
        return favorite;
    }

    public float getRate() {
        return rate;
    }

    public int getReview() {
        return review;
    }

    public int getCid() {
        return cid;
    }
}
