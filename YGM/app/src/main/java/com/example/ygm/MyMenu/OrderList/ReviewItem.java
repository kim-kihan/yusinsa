package com.example.ygm.MyMenu.OrderList;

import java.io.Serializable;

public class ReviewItem implements Serializable {

    private String pid;
    private String sid;
    private String img;
    private String name;
    private String option;

    public ReviewItem(String pid, String sid, String img, String name, String option) {
        this.pid = pid;
        this.sid = sid;
        this.img = img;
        this.name = name;
        this.option = option;
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
}
