package com.example.livetvapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.livetvapp.R;
import com.example.livetvapp.ui.mobile.home.MainActivity;
import com.example.livetvapp.ui.player.PlayerActivity;
import com.example.livetvapp.ui.tv.home.TvHomeActivity;
import com.example.livetvapp.util.PreferenceUtils;
import com.example.livetvapp.util.UiModeHelper;

public class SplashActivity extends AppCompatActivity {
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        viewModel.setDataReady(true);
        startBootSequence();
    }

    private void startBootSequence() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean isTv = UiModeHelper.isTelevision(this);
            if (isTv && PreferenceUtils.isBootToPlayerEnabled(this)) {
                Intent intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("channelIndex", 0);
                startActivity(intent);
            } else if (isTv) {
                startActivity(new Intent(this, TvHomeActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        }, 3000);
    }
}
