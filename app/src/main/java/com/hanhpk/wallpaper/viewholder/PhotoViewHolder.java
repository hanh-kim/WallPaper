package com.hanhpk.wallpaper.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanhpk.wallpaper.R;

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ImageView imgPhoto;
    public TextView tvViews;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.imgPhoto = itemView.findViewById(R.id.imgItemPhoto);
        this.tvViews = itemView.findViewById(R.id.tvViews);
    }
}
