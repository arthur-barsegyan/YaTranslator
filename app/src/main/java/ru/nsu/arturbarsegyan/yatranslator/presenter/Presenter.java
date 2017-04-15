package ru.nsu.arturbarsegyan.yatranslator.presenter;

import android.content.Context;

import com.couchbase.lite.android.AndroidContext;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.DBManager;
import ru.nsu.arturbarsegyan.yatranslator.DataManager;
import ru.nsu.arturbarsegyan.yatranslator.ModelBundle;
import ru.nsu.arturbarsegyan.yatranslator.Observer;

import ru.nsu.arturbarsegyan.yatranslator.model.Model;
import ru.nsu.arturbarsegyan.yatranslator.model.ModelImpl;
import ru.nsu.arturbarsegyan.yatranslator.view.View;

// TODO: Presenter depends from CouchBase Context
public class Presenter implements Observer {
    private String TAG = Presenter.class.getSimpleName();

    private DataManager dataManager;
    private Model model;
    private View view;
    private Context appContext;

    private List<String> supportedLangs;
    private String currentSourceLang;
    private String currentDestLang;

    public Presenter(PresenterBundle bundle) {
        appContext = bundle.getContext();
        view = bundle.getView();

        dataManager = new DBManager(new AndroidContext(appContext));

        // We can create Model without saving abilities (part functionality)
        // Catch exception from DataManger and using DataStumManager (Stub class)
        model = new ModelImpl(dataManager);
        model.subscribeObserver(this);
        supportedLangs = model.getAvailableLanguages();

        view.setLanguageList(supportedLangs);
        setupDefaultLanguages();
    }


    private void setupDefaultLanguages() {
        currentSourceLang = "Английский";
        currentDestLang = "Русский";

        model.setSourceLanguage(currentSourceLang);
        model.setDestinationLanguage(currentDestLang);

        updateViewLanguageDirection();
    }

    public void addView(View view) {
        this.view = view;
    }

    public void updateViewLanguageDirection() {
        view.setSourceLanguage(currentSourceLang);
        view.setDestinationLanguage(currentDestLang);
    }

    @Override
    public void update(ModelBundle bundle) {
        if (bundle.isLanguagesSupportUpdated()) {
            supportedLangs = bundle.getSupportLanguages();
            view.setLanguageList(supportedLangs);
        }

        if (bundle.isTranslationUpdated())
            view.setTranslationViewText(bundle.getLastTranslationText());
    }

    public void getTranslation(String userString) {
        model.requestTranslation(userString);
    }

    public void setSourceLanguage(String sourceLanguage) {
        model.setSourceLanguage(sourceLanguage);
    }

    public void setDestinationLanguage(String destinationLanguage) {
        model.setDestinationLanguage(destinationLanguage);
    }

    public void swapTranslationLanguages() {
        model.swapTranslationLanguages();

        String tempLang = currentSourceLang;
        currentSourceLang = currentDestLang;
        currentDestLang = tempLang;

        updateViewLanguageDirection();
    }
}