package com.example.livetvapp.ui.mobile.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.livetvapp.R;
import com.example.livetvapp.data.model.Channel;
import com.example.livetvapp.ui.player.PlayerActivity;
import com.example.livetvapp.ui.settings.SettingsFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MobileHomeViewModel viewModel;
    private ViewPager2 bannerPager;
    private RecyclerView categoryRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MobileHomeViewModel.class);
        bannerPager = findViewById(R.id.bannerPager);
        categoryRecycler = findViewById(R.id.categoryRecycler);

        categoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        categoryRecycler.setAdapter(new ChannelCategoryAdapter(new ChannelCategoryAdapter.OnChannelClickListener() {
            @Override
            public void onChannelClicked(Channel channel) {
                startPlayer(channel);
            }
        }));

        viewModel.getChannelList().observe(this, channels -> {
            if (channels != null) {
                ((ChannelCategoryAdapter) categoryRecycler.getAdapter()).submitList(channels);
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

    private void startPlayer(Channel channel) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("selectedChannelUrl", channel.getStreamUrl());
        intent.putExtra("selectedChannelName", channel.getName());
        startActivity(intent);
    }

    private void openSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .addToBackStack("settings")
                .commit();
    }
}
