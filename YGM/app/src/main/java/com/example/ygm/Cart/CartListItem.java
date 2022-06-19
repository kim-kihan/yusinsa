package com.example.ygm.Cart;

import java.io.Serializable;

public class CartListItem implements Serializable {

    private String pk;
    private String pid;
    private String sid;
    private String img;
    private String name, option;
    private int price;
    private int num;
    private int stock;
    private boolean selected;

    public CartListItem(String pk, String pid, String sid, String img, String name, String option, int price, int num, int stock) {
        this.pk = pk;
        this.pid = pid;
        this.sid = sid;
        this.img = img;
        this.name = name;
        this.option = option;
        this.price = price;
        this.num = num;
        this.stock = stock;
        selected = (stock > 0) ? true : false;
    }

    public String getPK() {
        return pk;
    }

    public String getPID() {
        return pid;
    }

    public String getSID() {
        return sid;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    public int getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public int getStock() {
        return stock;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
