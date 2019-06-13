package com.example.cats;

import android.app.Application;

import com.example.cats.data.Repository;

public class CatsApp extends Application {
    private Repository repository;
    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);
    }

    public Repository getRepository() {
        return repository;
    }
}
