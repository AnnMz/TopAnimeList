package com.example.mazunina.topanimelist.domain.usecase;

import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.domain.repository.ContentRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopContentListUseCase {
    private final ContentRepository repository;

    public TopContentListUseCase(ContentRepository repository) {
        this.repository = repository;
    }

    public Single<List<ContentModel>> getTopAnimeList() {
        return repository.getTopAnimeList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<ContentModel>> getTopMangaList() {
        return repository.getTopMangaList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
