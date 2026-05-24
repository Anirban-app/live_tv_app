package com.example.livetvapp.ui.mobile.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.livetvapp.data.model.Channel;
import java.util.ArrayList;
import java.util.List;

public class MobileHomeViewModel extends ViewModel {
    private final MutableLiveData<List<Channel>> channelList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Channel>> getChannelList() {
        return channelList;
    }

    public void setChannels(List<Channel> channels) {
        channelList.setValue(channels);
    }
}
