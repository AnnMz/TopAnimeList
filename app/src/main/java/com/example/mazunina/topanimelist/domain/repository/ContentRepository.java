package com.example.mazunina.topanimelist.domain.repository;

import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.model.ContentModel;

import java.util.List;

import io.reactivex.Single;

public interface ContentRepository {
    Single<List<ContentModel>> getTopAnimeList();

    Single<List<ContentModel>> getTopMangaList();

    Single<ContentDetailModel> getAnimeDetail(long id);

    Single<ContentDetailModel> getMangaDetail(long id);
}
