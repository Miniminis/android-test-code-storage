package com.example.dogsapplication.view.model;

public class SmsInfo {

    private String to;
    private String text;
    private String imageUrl;

    public SmsInfo(String to, String text, String imageUrl) {
        this.to = to;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
