package com.example.ygm.Category;

public class CategoryItem {

    private String img;
    private String title_kor;
    private String title_eng;
    private String[] sub = new String[5];

    public CategoryItem(String img, String title_kor, String title_eng, String sub1, String sub2, String sub3, String sub4, String sub5) {
        this.img = img;
        this.title_kor = title_kor;
        this.title_eng = title_eng;
        sub[0] = sub1;
        sub[1] = sub2;
        sub[2] = sub3;
        sub[3] = sub4;
        sub[4] = sub5;
    }

    public String getImg() {
        return img;
    }

    public String getTitle_kor() {
        return title_kor;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public String getSub1() {
        return sub[0];
    }

    public String getSub2() {
        return sub[1];
    }

    public String getSub3() {
        return sub[2];
    }

    public String getSub4() {
        return sub[3];
    }

    public String getSub5() {
        return sub[4];
    }
}
