package com.example.administrator.finalexam.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FeedResponse {

    @SerializedName("feeds")
    private List<Feed> feed;
    @SerializedName("success")
    private boolean success;

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

