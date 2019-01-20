package com.example.mazunina.topanimelist.presentation.di.component;

import com.example.mazunina.topanimelist.presentation.di.module.AppModule;
import com.example.mazunina.topanimelist.presentation.App;
import com.example.mazunina.topanimelist.presentation.presenter.ContentDetailPresenter;
import com.example.mazunina.topanimelist.presentation.presenter.TopContentListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(App application);

    void inject(TopContentListPresenter presenter);

    void inject(ContentDetailPresenter presenter);
}
