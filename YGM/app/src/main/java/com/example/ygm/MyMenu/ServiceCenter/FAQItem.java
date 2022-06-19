package com.example.ygm.MyMenu.ServiceCenter;

public class FAQItem {

    private String category;
    private String title;
    private String body;
    private boolean expand;

    public FAQItem(String category, String title, String body) {
        this.category = category;
        this.title = title;
        this.body = body;
        expand = false;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
