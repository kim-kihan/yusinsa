package com.example.ygm.MyMenu.ServiceCenter;

public class NoticeItem {
    String title;
    String date;
    String body;
    boolean expand;

    public NoticeItem(String title, String date, String body) {
        this.title = title;
        this.date = date;
        this.body = body;
        expand = false;
    }
}
