package ru.nsu.arturbarsegyan.yatranslator.presenter;


import android.content.Context;

public class PresenterBundle {
    private Context appContext;

    public PresenterBundle(Context appContext) {
        this.appContext = appContext;
    }

    public Context getContext() {
        return appContext;
    }
}
