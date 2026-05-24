package com.example.livetvapp.ui.player;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.livetvapp.R;
import com.example.livetvapp.data.model.Channel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private PlayerViewModel viewModel;
    private PlayerView playerView;
    private ExoPlayer player;
    private View overlay;
    private TextView overlayChannelName;
    private final Handler overlayHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        playerView = findViewById(R.id.playerView);
        overlay = findViewById(R.id.channelOverlay);
        overlayChannelName = findViewById(R.id.overlayChannelName);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        viewModel.getChannelList().observe(this, channels -> {
            if (channels != null && !channels.isEmpty()) {
                viewModel.setSelectedIndex(getIntent().getIntExtra("channelIndex", 0));
            }
        });

        viewModel.getSelectedIndex().observe(this, index -> {
            Channel channel = viewModel.getCurrentChannel();
            if (channel != null) {
                playChannel(channel);
                showChannelOverlay(channel.getName());
            }
        });

        viewModel.setChannels(createSampleChannels());
    }

    private List<Channel> createSampleChannels() {
        List<Channel> list = new ArrayList<>();
        list.add(new Channel("1", "Sports Live", "", "https://example.com/live/sports.m3u8"));
        list.add(new Channel("2", "News Center", "", "https://example.com/live/news.m3u8"));
        list.add(new Channel("3", "Movie Stream", "", "https://example.com/live/movies.m3u8"));
        return list;
    }

    private void playChannel(Channel channel) {
        player.setMediaItem(MediaItem.fromUri(channel.getStreamUrl()));
        player.prepare();
        player.play();
    }

    private void showChannelOverlay(String channelName) {
        overlayChannelName.setText(channelName);
        overlay.setVisibility(View.VISIBLE);
        overlayHandler.removeCallbacksAndMessages(null);
        overlayHandler.postDelayed(() -> overlay.setVisibility(View.GONE), 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            viewModel.setSelectedIndex(viewModel.getSelectedIndex().getValue() - 1);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            viewModel.setSelectedIndex(viewModel.getSelectedIndex().getValue() + 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overlayHandler.removeCallbacksAndMessages(null);
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
