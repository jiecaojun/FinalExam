package com.example.administrator.finalexam.bean;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.finalexam.R;

public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

 public static TextView viewID;
 public static ImageView viewImage;
 public static TextView viewName;

 public MyViewHolder(@NonNull View itemView) {
     super(itemView);
     viewID = (TextView)itemView.findViewById(R.id.tv_ID);
     viewImage = (ImageView)itemView.findViewById(R.id.tv_image);
     int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
     viewImage.setMaxHeight((int) (heightPixels*0.2));
     viewName = (TextView) itemView.findViewById(R.id.tv_name);
 }

    public static void updateUI(Feed message) {
        viewID.setText(message.getStudeng_id());
        viewName.setText(message.getUser_name());
        String url = message.getImage_url();
        Glide.with(viewImage.getContext()).load(url).into(viewImage);
    }

    @Override
    public void onClick(View v) {

    }
}
