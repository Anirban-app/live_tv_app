package com.example.livetvapp.ui.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {
    private final MutableLiveData<Boolean> dataReady = new MutableLiveData<>(false);

    public LiveData<Boolean> isDataReady() {
        return dataReady;
    }

    public void setDataReady(boolean ready) {
        dataReady.setValue(ready);
    }
}
