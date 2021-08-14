package com.hanhpk.wallpaper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.exception.NoInternetConnectionException;
import com.hanhpk.wallpaper.util.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Utils.checkNetwork(this)){
            try {
                throw new NoInternetConnectionException();
            } catch (NoInternetConnectionException e) {
                e.printStackTrace();
            }
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new PhotoListFragment()).commitNow();
    }
}