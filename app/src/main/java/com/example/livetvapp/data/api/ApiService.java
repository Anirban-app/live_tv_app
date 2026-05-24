package com.example.livetvapp.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/live/channels")
    Call<ResponseBody> getChannels();

    @POST("api/auth/login")
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password);
}
