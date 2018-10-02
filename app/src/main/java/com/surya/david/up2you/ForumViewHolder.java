package com.surya.david.up2you;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumViewHolder extends RecyclerView.ViewHolder {
    public TextView jdl, nm,tag,kategori;
    public ImageView img;
    public ForumViewHolder(View itemView) {
        super(itemView);
        jdl = itemView.findViewById(R.id.jdl_thread);
        img = itemView.findViewById(R.id.img_thread);
        nm = itemView.findViewById(R.id.nm_thread);
        tag = itemView.findViewById(R.id.tag_thread);
        kategori = itemView.findViewById(R.id.ktgr_thread);
    }
}
