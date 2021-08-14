package com.hanhpk.wallpaper.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.adapter.ViewPagerAdapter;
import com.hanhpk.wallpaper.databinding.FragmentPhotoShowBinding;
import com.hanhpk.wallpaper.models.Photo;
import com.hanhpk.wallpaper.models.Photos;
import com.hanhpk.wallpaper.viewmodel.PhotoViewModel;
import java.util.List;

/**
 * //  created by PhungKimHanh
 *
 */

public class PhotoShowFragment extends Fragment {


    private int position;
    private static final String POSITION_KEY = "position";
    private static final String PHOTOS_KEY = "photos";
    private ViewPagerAdapter adapter;

    private List<Photo> photoList;
    private Photos photos;
    private PhotoViewModel photoViewModel;
    private ActivityResultLauncher<String> mRequestPermissionResult;
    private FragmentPhotoShowBinding binding;

    private final int TYPE_FULL_HD = 1;
    private final int TYPE_MEDIUM = 2;
    private final int TYPE_LOW = 3;
    private int type = 0;
    boolean isDownloading = false;


    public PhotoShowFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhotoShowFragment newInstance(int position) {
        PhotoShowFragment fragment = new PhotoShowFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = (int) getArguments().get(POSITION_KEY);
            // photos = (Photos) getArguments().get(PHOTOS_KEY);
        }

        initData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_show, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        //  viewPager2.setCurrentItem(position, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.vpg2Photo.setCurrentItem(position, true);
            }
        }, 10);
        binding.vpg2Photo.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                isDownloading = false;
                photoViewModel.isDownloading().setValue(isDownloading);
            }

        });

        // click listener
        binding.fabDownload.setOnClickListener(v -> {
            if (!isDownloading) {
                isDownloading = true;
            } else {
                isDownloading = false;
            }
            photoViewModel.isDownloading().setValue(isDownloading);
            //  askToDownload();

        });

        binding.fabDownloadHD.setOnClickListener(v -> {
            type = TYPE_FULL_HD;
            photoViewModel.isDownloading().setValue(false);
            askToDownload();
        });
        binding.fabDownloadMedium.setOnClickListener(v -> {
            type = TYPE_MEDIUM;
            photoViewModel.isDownloading().setValue(false);
            askToDownload();
        });
        binding.fabDownloadLow.setOnClickListener(v -> {
            type = TYPE_LOW;
            photoViewModel.isDownloading().setValue(false);
            askToDownload();
        });

        photoViewModel.isDownloading().observe(getViewLifecycleOwner(), downloading -> {
            if (downloading) {
                binding.fabGroup.setVisibility(View.VISIBLE);
            } else {
                binding.fabGroup.setVisibility(View.GONE);
            }
        });
    }

    private void init(View view) {
        photoViewModel.isDownloading().setValue(false);
        photoViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), photos -> {
            photoList = photos;
            adapter.setData(photos);
            binding.vpg2Photo.setAdapter(adapter);
        });


    }

    private void initData() {
        photoViewModel = PhotoViewModel.getInstance(this);
        adapter = new ViewPagerAdapter(requireActivity());
        mRequestPermissionResult = requestPermissionResult();
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startDownloadImage();
            } else {
                mRequestPermissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            startDownloadImage();
        }
    }

    private ActivityResultLauncher<String> requestPermissionResult() {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                startDownloadImage();
            } else {
                Toast.makeText(getActivity(), "Quyền truy cập vào thư viện ảnh bị từ chối!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void startDownloadImage() {
        Photo p = photoList.get(binding.vpg2Photo.getCurrentItem());
        String url = null;
        if (type == TYPE_FULL_HD) {
            url = p.getUrlL();
        } else if (type == TYPE_MEDIUM) {
            url = p.getUrlC();
        } else if (type == TYPE_LOW) {
            url = p.getUrlZ();
        } else {
            url = "";
            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Wall Paper: " + p.getTitle());
        request.setDescription("Tải ảnh:" + p.getTitle());
        // request.setDestinationInExternalPublicDir(String.valueOf(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)), "wallpaper_" + System.currentTimeMillis() + ".jpg");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "wallpaper_" + System.currentTimeMillis() + ".jpg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) requireActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }


    private void askToDownload() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setTitle("Wall Paper");
        builder.setMessage("Bạn có xác nhận tải ảnh này không?");

        builder.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setPositiveButton("Xác nhận", (dialog, which) -> {
            checkPermission();
        });

        builder.show();

    }


}