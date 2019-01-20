package com.example.mazunina.topanimelist.domain.usecase;

import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.repository.ContentRepository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContentDetailUseCase {
    private final ContentRepository repository;

    public ContentDetailUseCase(ContentRepository repository) {
        this.repository = repository;
    }

    public Single<ContentDetailModel> getAnimeDetail(long id) {
        return repository.getAnimeDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<ContentDetailModel> getMangaDetail(long id) {
        return repository.getMangaDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
