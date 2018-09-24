package com.surya.david.up2you;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumViewHolder extends RecyclerView.ViewHolder {
    public TextView jdl;
    public ImageView img;
    public ForumViewHolder(View itemView) {
        super(itemView);
        jdl = itemView.findViewById(R.id.jdl_berita);
        img = itemView.findViewById(R.id.img_berita);
    }
}
