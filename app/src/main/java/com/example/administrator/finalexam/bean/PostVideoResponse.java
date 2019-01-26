package com.example.administrator.finalexam.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PostVideoResponse {

    @SerializedName("item")
    private Feed feed;
    @SerializedName("success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}