package com.surya.david.up2you;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class NewsAdapter extends RecyclerView.ViewHolder{
    public TextView mTitle;
    public ImageView mImage;
    public TextView mKota;
    public TextView mDate;
    public TextView mCategory;

    public NewsAdapter(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.jdl_berita);
        mImage = itemView.findViewById(R.id.img_berita);
        mKota = itemView.findViewById(R.id.kt_berita);
        mDate = itemView.findViewById(R.id.tgl_berita);
        mCategory = itemView.findViewById(R.id.ktgr_berita);
    }
}
