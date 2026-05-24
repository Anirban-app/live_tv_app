package com.example.livetvapp.ui.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.livetvapp.data.model.Channel;
import java.util.ArrayList;
import java.util.List;

public class PlayerViewModel extends ViewModel {
    private final MutableLiveData<List<Channel>> channelList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> selectedIndex = new MutableLiveData<>(0);

    public LiveData<List<Channel>> getChannelList() {
        return channelList;
    }

    public LiveData<Integer> getSelectedIndex() {
        return selectedIndex;
    }

    public void setChannels(List<Channel> channels) {
        channelList.setValue(channels);
    }

    public void setSelectedIndex(int index) {
        List<Channel> channels = channelList.getValue();
        if (channels == null || channels.isEmpty()) {
            selectedIndex.setValue(0);
            return;
        }
        int size = channels.size();
        if (index < 0) {
            index = size - 1;
        } else if (index >= size) {
            index = 0;
        }
        selectedIndex.setValue(index);
    }

    public Channel getCurrentChannel() {
        List<Channel> channels = channelList.getValue();
        Integer index = selectedIndex.getValue();
        if (channels == null || channels.isEmpty() || index == null || index < 0 || index >= channels.size()) {
            return null;
        }
        return channels.get(index);
    }
}
