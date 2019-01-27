package com.example.administrator.finalexam;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.finalexam.R;
import com.example.administrator.finalexam.bean.PostVideoResponse;
import com.example.administrator.finalexam.network.IMiniDouyinService;
import com.example.administrator.finalexam.utils.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PriviewAndPost extends AppCompatActivity {

    private Button btnExit;
    private Button Post;
    private String path;
    private String imagePath;
    private VideoView videoView;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priview_and_post);
        path=getIntent().getStringExtra("path");

        uri = Uri.parse(path);
        videoView = findViewById(R.id.video_pre);
        videoView.setVideoURI(uri);
        videoView.start();

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVideo();
            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriviewAndPost.this.finish();
            }
        });



    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_left);
    }

    public void postVideo(){
        Bitmap bitmap = getVideoThumb(path);
        imagePath = saveBitmapToSDCard(bitmap,path);

        getResponseWithRetrofitAsyncWithVideo(new Callback<PostVideoResponse>() {
            @Override public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response){
                Toast.makeText(getApplicationContext(),"上传成功！！",Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<PostVideoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"上传失败！！",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public  void getResponseWithRetrofitAsyncWithVideo(Callback<PostVideoResponse> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.108.10.39:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMiniDouyinService.class).creatVideo("1120170000","hhhhh",getMultipartFromUri("cover_image",Uri.parse(imagePath)),getMultipartFromUri("video",uri) ).
                enqueue(callback);
    }
    public MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        File f = new File(ResourceUtils.getRealPath(PriviewAndPost.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    public  static Bitmap getVideoThumb(String path) {

        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(path);

        return media.getFrameAtTime();
    }

    public static String  saveBitmapToSDCard(Bitmap bitmap, String path) {
        String newpath = path.substring(0,path.length()-4)+".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
                return newpath;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
