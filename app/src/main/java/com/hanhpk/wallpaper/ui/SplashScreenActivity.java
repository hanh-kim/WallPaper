package com.hanhpk.wallpaper.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.hanhpk.wallpaper.R;
import com.hanhpk.wallpaper.databinding.ActivitySplashScreenBinding;

/**
 * //  created by PhungKimHanh
 *
 */

public class SplashScreenActivity extends AppCompatActivity {


    ActivitySplashScreenBinding binding;
    ObjectAnimator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        animator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100);
        animator.setDuration(2000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator.cancel();
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        });
        animator.start();
    }


}