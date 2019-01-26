package com.example.administrator.finalexam.network;

import com.example.administrator.finalexam.bean.FeedResponse;
import com.example.administrator.finalexam.bean.PostVideoResponse;

import java.security.spec.PSSParameterSpec;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {

    @Multipart
    @POST("/minidouyin/video/")
    Call<PostVideoResponse>
    creatVideo(
            @Query("student_id") String student_id,
            @Query("user_name") String user_name,
            @Part MultipartBody.Part cover_image,
            @Part MultipartBody.Part video
    );


    @GET("/minidouyin/feed/")
    Call<FeedResponse> getResour();

}

