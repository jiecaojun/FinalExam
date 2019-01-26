//package com.example.administrator.finalexam.bean;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.administrator.finalexam.MainActivity;
//import com.example.administrator.finalexam.R;
//
//
///**
// * 适配器
// */
//public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {
//
//    private static final String TAG = "GreenAdapter";
//
//    private int mNumberItems;
//
//    private final ListItemClickListener mOnClickListener;
//
//    private static int viewHolderCount;
//
//    public GreenAdapter(int numListItems, ListItemClickListener listener) {
//        mNumberItems = numListItems;
//        mOnClickListener = listener;
//        viewHolderCount = 0;
//    }
//
//
//    @NonNull
//    @Override
//    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        ImageView imageView = new ImageView(viewGroup.getContext());
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        imageView.setAdjustViewBounds(true);
//        return new NumberViewHolder(imageView);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
//        ImageView iv = (ImageView) NumberViewHolder.itemView;
//
//        String url = mFeeds.get(i).getImage_url();
//        Glide.with(iv.getContext()).load(url).into(iv);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mNumberItems;
//    }
//
//    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private final TextView viewHolderIndex;
//        private final TextView listItemNumberView;
//
//        public NumberViewHolder(@NonNull View itemView) {
//            super(itemView);
//            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
//            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(int position) {
//            listItemNumberView.setText(String.valueOf(position));
//
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            int clickedPosition = getAdapterPosition();
//            if (mOnClickListener != null) {
//                mOnClickListener.onListItemClick(clickedPosition);
//            }
//        }
//    }
//
//    public interface ListItemClickListener {
//        void onListItemClick(int clickedItemIndex);
//    }
//}
