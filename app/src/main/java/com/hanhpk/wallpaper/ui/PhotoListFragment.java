package com.hanhpk.wallpaper.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.adapter.PhotoAdapter;
import com.hanhpk.wallpaper.models.Photo;
import com.hanhpk.wallpaper.models.Photos;
import com.hanhpk.wallpaper.events.OnPhotoItemClickListener;
import com.hanhpk.wallpaper.recyclerview.EndlessRecyclerViewScrollListener;
import com.hanhpk.wallpaper.recyclerview.ItemDecorationAlbumColumns;
import com.hanhpk.wallpaper.viewmodel.PhotoViewModel;

/**
 * //  created by PhungKimHanh
 *
 */

public class PhotoListFragment extends Fragment {

    private Photos photos;
    private RecyclerView rcvPhoto;
    private PhotoAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ProgressBar progressBar;
    private PhotoViewModel photoViewModel;


    // TODO: Rename and change types of parameters
    private static final String PHOTOS_KEY = "photos";

    public PhotoListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhotoListFragment newInstance(Photos photos) {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PHOTOS_KEY, photos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photos = (Photos) getArguments().get(PHOTOS_KEY);
        }
        initData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        photoViewModel.loadPhoto(1);
        photoViewModel.isLoading().observe(getViewLifecycleOwner(),isLoading->{
            if (isLoading){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        });
        photoViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), photos -> adapter.setData(photos));
        photoViewModel.getError().observe(getViewLifecycleOwner(),error -> {
            Toast.makeText(requireActivity(), ""+error, Toast.LENGTH_SHORT).show();
        });

    }

    private void initUI(View view) {
        rcvPhoto = view.findViewById(R.id.rcvPhoto);
        progressBar = view.findViewById(R.id.prbLoad);
        ItemDecorationAlbumColumns columns = new ItemDecorationAlbumColumns(2,2);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                progressBar.setVisibility(View.VISIBLE);
                photoViewModel.loadPhoto((page+1));

                Log.d("TAG", "page:" + page);
            }
        };
        rcvPhoto.setLayoutManager(staggeredGridLayoutManager);
        rcvPhoto.addOnScrollListener(scrollListener);
        rcvPhoto.addItemDecoration(columns);
        rcvPhoto.setAdapter(adapter);
        rcvPhoto.setHasFixedSize(false);

    }

    private void initData() {
        photoViewModel = PhotoViewModel.getInstance(this);
        adapter = new PhotoAdapter(getActivity(), new OnPhotoItemClickListener() {
            @Override
            public void onClick(Photo photo, int position) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, PhotoShowFragment.newInstance(position));
                transaction.addToBackStack(this.getClass().getName());
                transaction.commit();
                Log.d("TAG", "position:" + position);
            }
        });



    }


}