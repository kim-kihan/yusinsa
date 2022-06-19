package com.example.ygm.MyMenu.Inquiry;

public class OneToOneInquiryItem {

    private String title;
    private String body;
    private String inquireDate;
    private String answer;
    private String answerDate;
    private String type;
    private String status;

    public OneToOneInquiryItem(String title, String body, String inquireDate, String answer, String answerDate, String type) {
        this.title = title;
        this.body = body;
        this.inquireDate = inquireDate;
        this.answer = answer;
        this.answerDate = answerDate;
        this.type = type;
        status = (answer == "null") ? "답변 대기" : "답변 완료";
    }

    public String getTitle() {
        return title;
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

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
