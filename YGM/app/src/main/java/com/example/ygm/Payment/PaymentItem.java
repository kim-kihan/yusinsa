package com.example.ygm.Payment;

import java.io.Serializable;

public class PaymentItem implements Serializable {

    private String pid;
    private String sid;
    private String img;
    private String name, option;
    private int price;
    private int num;
    private String cid;

    public PaymentItem(String pid, String sid, String img, String name, String option, int price, int num) {
        this.pid = pid;
        this.sid = sid;
        this.img = img;
        this.name = name;
        this.option = option;
        this.price = price;
        this.num = num;
    }

    public PaymentItem(String pid, String sid, String img, String name, String option, int price, int num, String cid) {
        this.pid = pid;
        this.sid = sid;
        this.img = img;
        this.name = name;
        this.option = option;
        this.price = price;
        this.num = num;
        this.cid = cid;
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

    public String getCID() {
        return cid;
    }
}
