package com.example.mazunina.topanimelist.data.network.api;

import com.example.mazunina.topanimelist.data.network.response.ContentDetailResponse;
import com.example.mazunina.topanimelist.data.network.response.TopContentListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JikanRestApi {
    @GET("top/anime")
    Single<TopContentListResponse> getTopAnimeList();

    @GET("top/manga")
    Single<TopContentListResponse> getTopMangaList();

    @GET("anime/{id}")
    Single<ContentDetailResponse> getAnimeDetail(@Path("id") long id);

    @GET("manga/{id}")
    Single<ContentDetailResponse> getMangaDetail(@Path("id") long id);
}
