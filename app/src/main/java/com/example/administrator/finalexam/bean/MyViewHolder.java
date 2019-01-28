package com.example.administrator.finalexam.bean;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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
//     viewID = (TextView)itemView.findViewById(R.id.tv_ID);
     viewImage = (ImageView)itemView.findViewById(R.id.tv_image);
//     int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
//     viewImage.setMaxHeight((int) (heightPixels*0.2));
//     viewName = (TextView) itemView.findViewById(R.id.tv_name);


     int width = ((Activity) viewImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
     ViewGroup.LayoutParams params = viewImage.getLayoutParams();
     //设置图片的相对于屏幕的宽高比
     params.width = width/2;
     params.height =  (int) (400 + Math.random() * 600) ;
     viewImage.setLayoutParams(params);
 }

    public static void updateUI(Feed message) {
//        viewID.setText(message.getStudeng_id());
//        viewName.setText(message.getUser_name());
        String url = message.getImage_url();
        Glide.with(viewImage.getContext()).load(url).into(viewImage);
    }

    @Override
    public void onClick(View v) {

//            int clickedPosition = getAdapterPosition();
//            if (mOnClickListener != null) {
//                mOnClickListener.onListItemClick(clickedPosition);
//            }

    }
}
