package com.icourier.qourier.Notification;


public class noticeItems {

    private String message;
    private String time;
    private String subject;
    private String sender;
    private String gender;


    public noticeItems(String message, String time, String subject, String sender, String gender) {
        this.message = message;
        this.time = time;
        this.subject = subject;
        this.sender = sender;
        this.gender = gender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
