package com.digital4africa.d4avictor.api.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {

    @GET("get_posts")
    Call<ResponseBody> getPosts();
}
