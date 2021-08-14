package com.hanhpk.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.models.Photo;
import com.hanhpk.wallpaper.models.Photos;
import com.hanhpk.wallpaper.events.OnPhotoItemClickListener;
import com.hanhpk.wallpaper.viewholder.PhotoViewHolder;

import java.util.List;

/**
 * //  created by PhungKimHanh
 *
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private Context context;
    private List<Photo> photoList;
    private Photos photos;
    private OnPhotoItemClickListener onPhotoItemClickListener;

    public PhotoAdapter(Context context, OnPhotoItemClickListener onPhotoItemClickListener) {
        this.context = context;
        this.onPhotoItemClickListener = onPhotoItemClickListener;
    }

    public void setData(List<Photo> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        if (photo == null) return;
        holder.tvViews.setText(photo.getViews());
        Glide.with(context).load(photo.getUrlL()).centerCrop().fitCenter().thumbnail(0.3f).into(holder.imgPhoto);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoItemClickListener.onClick(photo, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (photoList != null) return photoList.size();
        return 0;
    }
}
