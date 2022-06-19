package com.example.ygm.Product;

public class OptionItem {

    String PID;
    String SID;
    String color;
    String size;
    String scount;

    public OptionItem(String PID, String SID, String color, String size, String scount) {
        this.color = color;
        this.PID = PID;
        this.SID = SID;
        this.size = size;
        this.scount = scount;
    }

    public String getScount() {
        return scount;
    }

    public String getColor() {
        return color;
    }

    public String getPID() {
        return PID;
    }

    public String getSID() {
        return SID;
    }

    public String getSize() {
        return size;
    }
}
