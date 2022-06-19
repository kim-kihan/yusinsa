package com.example.ygm;

import android.app.Application;

public class User extends Application {

    private String UID;
    private String ID, PW;
    private String Uname;
    private String phone;
    private String email;
    private String birthdate;
    private int height, weight;
    private int exp;
    private int lv;
    private String grade;
    private String regdate;
    private boolean pushAlarm;
    private int point;

    String PID;


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        lv = (exp <= 2000) ? 1 : (exp <= 10000) ? 2 : (exp <= 100000) ? 3 : (exp <= 200000) ? 4 : (exp <= 500000) ? 5 : (exp <= 1000000) ? 6 : (exp <= 2000000) ? 7 : 8;
        String[] grade = {"뉴비", "루키", "멤버", "브론즈", "실버", "골드", "플래티넘", "다이아몬드"};
        this.grade = grade[lv - 1];
    }

    public int getLV() {
        return lv;
    }

    public String getGrade() {
        return grade;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public boolean isPushAlarm() {
        return pushAlarm;
    }

    public void setPushAlarm(boolean pushAlarm) {
        this.pushAlarm = pushAlarm;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
