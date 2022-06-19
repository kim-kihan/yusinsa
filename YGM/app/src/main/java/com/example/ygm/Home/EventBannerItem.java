package com.example.ygm.Home;

public class EventBannerItem {

    private String pk;
    private String thumbnail;

    public EventBannerItem(String pk, String thumbnail) {
        this.pk = pk;
        this.thumbnail = thumbnail;
    }

    public String getPK() {
        return pk;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
