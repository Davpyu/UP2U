package com.surya.david.up2you;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.ViewHolder {
    public TextView mTitle;
    public ImageView mImage;
    public TextView mKota;
    public TextView mDate;
    public TextView mCategory;

    public NewsAdapter(@NonNull View itemView) {
        super(itemView);
        mTitle = (TextView)itemView.findViewById(R.id.judulBerita);
        mImage = (ImageView)itemView.findViewById(R.id.img_berita);
        mKota = (TextView)itemView.findViewById(R.id.kotaBerita);
        mDate = (TextView)itemView.findViewById(R.id.tglBerita);
        mCategory = (TextView)itemView.findViewById(R.id.c_news);
    }
}
