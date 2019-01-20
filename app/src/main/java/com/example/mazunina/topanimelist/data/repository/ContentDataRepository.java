package com.example.mazunina.topanimelist.data.repository;

import com.example.mazunina.topanimelist.data.mapper.ContentDataMapper;
import com.example.mazunina.topanimelist.data.network.api.JikanApiClient;
import com.example.mazunina.topanimelist.data.network.response.BaseResponse;
import com.example.mazunina.topanimelist.data.network.response.TopContentListResponse;
import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.domain.repository.ContentRepository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ContentDataRepository implements ContentRepository {
    private final ContentDataMapper mapper;

    private JikanApiClient client;

    @Inject
    public ContentDataRepository(ContentDataMapper mapper, File cacheDir) {
        this.mapper = mapper;
        client = new JikanApiClient(cacheDir);
    }

    @Override
    public Single<List<ContentModel>> getTopAnimeList() {
        return client.getApi()
                .getTopAnimeList()
                .map(response -> {
                    checkError(response);
                    return response;
                })
                .map(TopContentListResponse::getEntities)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(mapper::transform)
                .toList();
    }

    @Override
    public Single<List<ContentModel>> getTopMangaList() {
        return client.getApi()
                .getTopMangaList()
                .map(response -> {
                    checkError(response);
                    return response;
                })
                .map(TopContentListResponse::getEntities)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(mapper::transform)
                .toList();
    }

    @Override
    public Single<ContentDetailModel> getAnimeDetail(long id) {
        return client.getApi()
                .getAnimeDetail(id)
                .map(response -> {
                    checkError(response);
                    return response;
                })
                .map(mapper::transformDetail);
    }

    @Override
    public Single<ContentDetailModel> getMangaDetail(long id) {
        return client.getApi()
                .getMangaDetail(id)
                .map(response -> {
                    checkError(response);
                    return response;
                })
                .map(mapper::transformDetail);
    }

    private void checkError(BaseResponse response) throws Exception {
        if (response.hasError()) {
            throw new Exception(response.getError());
        }
    }
}
