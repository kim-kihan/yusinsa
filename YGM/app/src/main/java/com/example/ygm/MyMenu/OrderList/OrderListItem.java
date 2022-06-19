package com.example.ygm.MyMenu.OrderList;

public class OrderListItem {

    private String pk;
    private String pid;
    private String sid;
    private String image;
    private String name;
    private String option;
    private int amount;
    private int num;
    private String date;
    private String address;

    public OrderListItem(String pk, String pid, String sid, String image, String name, String option, int amount, int num, String date, String address) {
        this.pk = pk;
        this.pid = pid;
        this.sid = sid;
        this.image = image;
        this.name = name;
        this.option = option;
        this.amount = amount;
        this.num = num;
        this.date = date;
        this.address = address;
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

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    public int getAmount() {
        return amount;
    }

    public int getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }
}
