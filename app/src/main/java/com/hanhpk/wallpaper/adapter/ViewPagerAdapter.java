package com.hanhpk.wallpaper.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hanhpk.wallpaper.ui.PhotoFragment;
import com.hanhpk.wallpaper.models.Photo;

import java.util.List;

/**
 * //  created by PhungKimHanh
 *
 */

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Photo> photoList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setData(List<Photo> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PhotoFragment.newInstance(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }
}
