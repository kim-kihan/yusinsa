package com.example.ygm.MyMenu.Inquiry;

public class ProductInquiryItem {

    private String userID;
    private String title;
    private String productName;
    private String body;
    private String inquireDate;
    private String answer;
    private String answerDate;
    private String image;
    private String type;
    private String status;

    public ProductInquiryItem(String userID, String title, String productName, String body, String inquireDate, String answer, String answerDate, String image, String type) {
        this.userID = userID;
        this.title = title;
        this.productName = productName;
        this.body = body;
        this.inquireDate = inquireDate;
        this.answer = answer;
        this.answerDate = answerDate;
        this.image = image;
        this.type = type;
        status = (answer == "null") ? "답변 대기" : "답변 완료";
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getProductName() {
        return productName;
    }

    public String getBody() {
        return body;
    }

    public String getInquireDate() {
        return inquireDate;
    }

    public String getAnswer() {
        return answer;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
