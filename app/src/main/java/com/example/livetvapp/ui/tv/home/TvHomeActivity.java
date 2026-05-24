package com.example.livetvapp.ui.tv.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.livetvapp.R;
import com.example.livetvapp.data.model.Channel;
import com.example.livetvapp.ui.player.PlayerActivity;
import java.util.ArrayList;
import java.util.List;

public class TvHomeActivity extends AppCompatActivity {
    private TvHomeViewModel viewModel;
    private RecyclerView channelGrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_home);

        viewModel = new ViewModelProvider(this).get(TvHomeViewModel.class);
        channelGrid = findViewById(R.id.tvChannelGrid);
        channelGrid.setLayoutManager(new GridLayoutManager(this, 3));
        ChannelCardAdapter adapter = new ChannelCardAdapter(new ChannelCardAdapter.OnChannelSelectedListener() {
            @Override
            public void onChannelSelected(Channel channel) {
                openPlayer(channel);
            }
        });
        channelGrid.setAdapter(adapter);

        viewModel.getChannelList().observe(this, channels -> {
            adapter.submitList(channels);
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

    private void openPlayer(Channel channel) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("selectedChannelUrl", channel.getStreamUrl());
        intent.putExtra("selectedChannelName", channel.getName());
        startActivity(intent);
    }
}
