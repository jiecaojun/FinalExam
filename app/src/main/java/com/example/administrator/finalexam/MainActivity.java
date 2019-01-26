package com.example.administrator.finalexam;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.finalexam.bean.Feed;
import com.example.administrator.finalexam.bean.FeedResponse;
import com.example.administrator.finalexam.network.IMiniDouyinService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private SmartRefreshLayout srfresh;
    private mAdapter recycleAdapter;
    private List<Feed> mFeeds = new ArrayList<>();
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},111);
        mHandler=new Handler();
        //初始化
        mRv=findViewById(R.id.rv);
        srfresh= findViewById(R.id.refreshLayout);
        initFlash();
    }
    public void initFlash(){
        recycleAdapter = new mAdapter( );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        mRv.setAdapter(recycleAdapter);
        //设置 Header 为 贝塞尔雷达 样式
        srfresh.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        srfresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        srfresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                //延时展示，延时2秒

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchFeed();
                        recycleAdapter.refresh(mFeeds);
                        refreshlayout.finishRefresh();

                    }
                },1000);

            }
        });
        srfresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        fetchFeed();
                        recycleAdapter.refresh(mFeeds);
                        refreshlayout.finishLoadmore();
                    }
                },1000);
            }

        });
        srfresh.setEnableAutoLoadmore(false);
        srfresh.autoRefresh();
    }


    public void fetchFeed(){

        getResponseWithRetrofitAsyncWithImg(new Callback<FeedResponse>() {
            @Override public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response){
                mFeeds = response.body().getFeed();
                Toast.makeText(getApplicationContext(),"获取成功！！",Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<FeedResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"获取失败！！",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public  void getResponseWithRetrofitAsyncWithImg(Callback<FeedResponse> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.108.10.39:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMiniDouyinService.class).getResour().
                enqueue(callback);
    }

    public class mAdapter extends RecyclerView.Adapter{
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ImageView imageView = new ImageView(viewGroup.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            return new MyViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView iv = (ImageView) viewHolder.itemView;

            String url = mFeeds.get(i).getImage_url();
            Glide.with(iv.getContext()).load(url).into(iv);
        }

        @Override
        public int getItemCount() {
            return mFeeds.size();
        }

        public void refresh(List<Feed> addList) { //增加数据
            int position = mFeeds.size();
            mFeeds.addAll(position, addList);
            notifyDataSetChanged();
        }

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private static TextView viewID;
        private static ImageView viewImage;
        private static TextView viewName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewID = (TextView)itemView.findViewById(R.id.tv_ID);
            viewImage = (ImageView)itemView.findViewById(R.id.tv_image);
            viewName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
