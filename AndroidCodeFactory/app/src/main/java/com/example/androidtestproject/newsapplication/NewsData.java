package com.example.androidtestproject.newsapplication;

import java.io.Serializable;

public class NewsData implements Serializable {

    //implements Serializable : 여러개의 뭉태기 json data 들을 받을 때, serialize 처리

    private String title;
    private String content;
    private String urlToImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
