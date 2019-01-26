package com.example.administrator.finalexam.bean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.finalexam.MainActivity;
import com.example.administrator.finalexam.R;

import java.util.List;

import static com.example.administrator.finalexam.bean.MyViewHolder.viewImage;

public class mAdatper extends RecyclerView.Adapter{
    private List<Feed> mFeeds;
    private static int viewHolderCount;
    public mAdatper(List<Feed> message) {
        mFeeds=message;
        viewHolderCount = 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);

        Glide.with(viewHolder.viewImage.getContext()).load(mFeeds.get(viewHolderCount).getImage_url()).into(viewHolder.viewImage);
        viewHolder.viewName.setText(mFeeds.get(viewHolderCount).getUser_name());
        viewHolder.viewID.setText(mFeeds.get(viewHolderCount).getStudeng_id());
                viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Feed message = mFeeds.get(i);
        MyViewHolder.updateUI(message);
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
