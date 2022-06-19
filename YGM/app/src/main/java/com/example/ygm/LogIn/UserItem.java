package com.example.ygm.LogIn;

public class UserItem {

    private String UID;
    private String ID, PW;
    private String Uname;
    private String phone;
    private String email;
    private String birthdate;
    private int height, weight;
    private int exp;
    private String regdate;
    private boolean pushAlarm;
    private int point;

    String PID;

    public UserItem(String UID, String ID, String PW, String Uname, String phone, String email, String birthdate, int height, int weight, int exp, boolean pushAlarm, int point, String regdate) {
        this.UID = UID;
        this.ID = ID;
        this.PW = PW;
        this.Uname = Uname;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.exp = exp;
        this.pushAlarm = pushAlarm;
        this.point = point;
        this.regdate = regdate;
    }

    public String getUID() {
        return UID;
    }

    public String getID() {
        return ID;
    }

    public String getPW() {
        return PW;
    }

    public String getUname() {
        return Uname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getExp() {
        return exp;
    }

    public String getRegdate() {
        return regdate;
    }

    public boolean isPushAlarm() {
        return pushAlarm;
    }

    public String getPID() {
        return PID;
    }

    public int getPoint() {
        return point;
    }
}
