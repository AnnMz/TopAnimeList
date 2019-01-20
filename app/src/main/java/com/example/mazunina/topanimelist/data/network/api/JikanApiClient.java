package com.example.mazunina.topanimelist.data.network.api;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JikanApiClient {
    private static final String API_BASE_URL = "https://api.jikan.moe/v3/";
    private static final int CACHE_SIZE = 3 * 1024 * 1024;

    private static JikanRestApi api;

    public JikanApiClient(File cacheDir) {
        api = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(getClient(cacheDir))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(JikanRestApi.class);
    }

    private OkHttpClient getClient(File cacheDir){
        Cache cache = new Cache(cacheDir, CACHE_SIZE);
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    public JikanRestApi getApi() {
        return api;
    }
}
