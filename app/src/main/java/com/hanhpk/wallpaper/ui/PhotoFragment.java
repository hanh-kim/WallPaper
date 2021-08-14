package com.hanhpk.wallpaper.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.models.Photo;


public class PhotoFragment extends Fragment {


    private Photo photo;

    // TODO: Rename parameter arguments, choose names that match
    private static final String PHOTO_KEY = "photo";

    public PhotoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(Photo photo) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putSerializable(PHOTO_KEY, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photo = (Photo) getArguments().get(PHOTO_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgPhoto = view.findViewById(R.id.imgPhoto);
        Glide.with(requireActivity()).load(photo.getUrlL()).into(imgPhoto);
    }
}