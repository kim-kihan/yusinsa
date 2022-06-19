package com.example.ygm.Product;

public class InquireItem {

    private String UID;
    private String title;
    private String PID;
    private String question;
    private String category;
    private String date;
    private String id;
    private String answer;
    private String answerdate;
    private String image;

    public InquireItem(String UID, String PID, String title, String question, String category, String date, String id, String answer, String answerdate, String image) {
        this.answer = answer;
        this.answerdate = answerdate;
        this.category = category;
        this.date = date;
        this.id = id;
        this.image = image;
        this.PID = PID;
        this.question = question;
        this.title = title;
        this.UID = UID;
    }

    public String getPID() {
        return PID;
    }

    public String getAnswerdate() {
        return answerdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getUID() {
        return UID;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
