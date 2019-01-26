package com.example.administrator.finalexam.bean;



import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("student_id")
    private  String studeng_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("image_url")
    private  String image_url;
    @SerializedName("video_url")
    private String video_url;

    public String getStudeng_id() {
        return studeng_id;
    }

    public void setStudeng_id(String studeng_id) {
        this.studeng_id = studeng_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
