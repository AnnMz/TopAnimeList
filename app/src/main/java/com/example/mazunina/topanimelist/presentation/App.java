package com.example.mazunina.topanimelist.presentation;

import android.app.Application;

import com.example.mazunina.topanimelist.presentation.di.component.AppComponent;
import com.example.mazunina.topanimelist.presentation.di.component.DaggerAppComponent;
import com.example.mazunina.topanimelist.presentation.di.module.AppModule;

import timber.log.Timber;

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        appComponent.inject(this);
        Timber.plant(new Timber.DebugTree());
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}
