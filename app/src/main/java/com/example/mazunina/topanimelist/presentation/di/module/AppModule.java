package com.example.mazunina.topanimelist.presentation.di.module;

import android.content.Context;

import com.example.mazunina.topanimelist.data.mapper.ContentDataMapper;
import com.example.mazunina.topanimelist.data.repository.ContentDataRepository;
import com.example.mazunina.topanimelist.domain.usecase.ContentDetailUseCase;
import com.example.mazunina.topanimelist.domain.usecase.TopContentListUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    ContentDataMapper providesContentDataMapper() {
        return new ContentDataMapper();
    }

    @Provides
    @Singleton
    ContentDataRepository providesContentDataRepository(ContentDataMapper mapper) {
        return new ContentDataRepository(mapper, context.getCacheDir());
    }

    @Provides
    TopContentListUseCase providesTopContentListUseCase(ContentDataRepository repository) {
        return new TopContentListUseCase(repository);
    }

    @Provides
    ContentDetailUseCase providesContentDetailUseCase(ContentDataRepository repository) {
        return new ContentDetailUseCase(repository);
    }
}
