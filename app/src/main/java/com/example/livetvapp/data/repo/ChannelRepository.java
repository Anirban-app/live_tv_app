package com.example.livetvapp.data.repo;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.livetvapp.data.api.ApiService;
import com.example.livetvapp.data.model.Channel;
import com.example.livetvapp.security.CryptoUtils;
import com.example.livetvapp.security.SecurityManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelRepository {
    private static final String BASE_URL = "https://api.example.com/";
    private final ApiService apiService;
    private final MutableLiveData<List<Channel>> channels = new MutableLiveData<>(new ArrayList<>());

    public ChannelRepository(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new com.example.livetvapp.data.api.EncryptedInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Channel>> getChannels() {
        return channels;
    }

    public void fetchChannels() {
        new Thread(() -> {
            try {
                Call<ResponseBody> call = apiService.getChannels();
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    String encrypted = response.body().string();
                    byte[] sessionKey = SecurityManager.deriveSessionKey(context);
                    String decrypted = CryptoUtils.decryptAesBase64(sessionKey, encrypted);
                    Type listType = new TypeToken<List<Channel>>() {}.getType();
                    List<Channel> fetched = new Gson().fromJson(decrypted, listType);
                    channels.postValue(fetched != null ? fetched : new ArrayList<>());
                    CryptoUtils.clear(sessionKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
