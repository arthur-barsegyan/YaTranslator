package ru.nsu.arturbarsegyan.yatranslator.presenter;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

import ru.nsu.arturbarsegyan.yatranslator.model.Model;
import ru.nsu.arturbarsegyan.yatranslator.model.ModelImpl;
import ru.nsu.arturbarsegyan.yatranslator.view.View;

public class Presenter {
    private Model model;
    private Context appContext;
    private View view;
    private Manager manager;

    public Presenter(PresenterBundle bundle) {
        appContext = bundle.getContext();
        try {
            manager = new Manager(new AndroidContext(appContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.d("Presenter", "DB Manager init error");
        }

        // We can create Model without DB abilities (part functionality)
        model = new ModelImpl(manager);
    }

    public void addView(View view) {
        this.view = view;
    }
}
