package com.example.ygm.Search;

public class SearchResultItem {

    private String PID;
    private String img;
    private String category;
    private String Pname;
    private int price;
    private int lcount;
    private float star;
    private int rcount;
    private int stock;

    public SearchResultItem(String PID, String img, String category, String Pname, int price, int lcount, float star, int rcount, int stock) {
        this.PID = PID;
        this.img = img;
        this.category = category;
        this.Pname = Pname;
        this.price = price;
        this.lcount = lcount;
        this.star = star;
        this.rcount = rcount;
        this.stock = stock;
    }

    public String getPID() {
        return PID;
    }

    public String getImg() {
        return img;
    }

    public String getCategory() {
        return category;
    }

    public String getPname() {
        return Pname;
    }

    public int getPrice() {
        return price;
    }

    public int getLcount() {
        return lcount;
    }

    public float getStar() {
        return star;
    }

    public int getRcount() {
        return rcount;
    }

    public int getStock() {
        return stock;
    }
}
