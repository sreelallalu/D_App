package com.dapp.dapplication.model;

/**
 * Created by sreelal on 30/3/18.
 */

public class ViewModel {
    String title;
    String content;
    String link;
    String date;

    public ViewModel(String title, String content, String link, String date) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.date = date;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
