package ru.nsu.arturbarsegyan.yatranslator.presenter;


import android.content.Context;

import ru.nsu.arturbarsegyan.yatranslator.view.View;

public class PresenterBundle {
    private Context appContext;
    private View view;

    public PresenterBundle(View view, Context appContext) {
        this.view = view;
        this.appContext = appContext;
    }

    public Context getContext() {
        return appContext;
    }

    public View getView() {
        return view;
    }
}
