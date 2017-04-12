package ru.nsu.arturbarsegyan.yatranslator.presenter;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.Observer;

import ru.nsu.arturbarsegyan.yatranslator.model.Model;
import ru.nsu.arturbarsegyan.yatranslator.model.ModelImpl;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateDirection;
import ru.nsu.arturbarsegyan.yatranslator.view.View;

public class Presenter implements Observer {
    private Model model;
    private Context appContext;
    private View view;
    private Manager manager;

    public Presenter(PresenterBundle bundle) {
        appContext = bundle.getContext();
        view = bundle.getView();

        try {
            manager = new Manager(new AndroidContext(appContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.d("Presenter", "DB Manager init error");
        }

        // We can create Model without DB abilities (part functionality)
        model = new ModelImpl(manager);
        model.subscribeObserver(this);
        updateAvailableLanguages();
    }

    private void updateAvailableLanguages() {
        SupportLanguages supportedLanguages = model.getAvailableLanguages();
        List<String> languages = new ArrayList<>();
        for (TranslateDirection currentLanguage : supportedLanguages.getDirections()) {
            languages.add(currentLanguage.getLanguageName());
        }

        view.setLanguageSpinner(languages);
    }

    public void addView(View view) {
        this.view = view;
    }

    @Override
    public void update(String translation) {
        view.setTranslationViewText(translation);
    }

    public void getTranslation(String userString) {
        model.requestTranslation(userString);
    }

    public void setSourceLanguage(String sourceLanguage) {
        model.setOriginalLanguage(sourceLanguage);
    }

    public void setDestinationLanguage(String destinationLanguage) {
        model.setLanguageOfTranslation(destinationLanguage);
    }
}
