package ru.nsu.arturbarsegyan.yatranslator.model;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import ru.nsu.arturbarsegyan.yatranslator.Observer;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateDirection;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateResponse;
import ru.nsu.arturbarsegyan.yatranslator.model.network.SupportLanguagesService;
import ru.nsu.arturbarsegyan.yatranslator.model.network.TranslatorService;

public class ModelImpl implements Model  {
    private static final String TAG = Model.class.getName();
    final Gson gson;
    private Retrofit httpSender;
    private final String dbName = "yaTranslatorDB";
    private DatabaseOptions options = new DatabaseOptions();
    private Manager dbManager;
    private Database db = null;

    private SupportLanguages supportLanguages = null;
    private String originalLanguage = "ru";
    private String languageOfTranslaton = "en";

    private String userLastTranslation = null;
    private Set<Observer> observers = new HashSet<>();

    public ModelImpl(Manager dbManager) {
        gson =  new GsonBuilder()
                .registerTypeAdapter(SupportLanguages.class, new LangsDeserializer())
                .create();
        httpSender = new Retrofit.Builder().baseUrl("https://translate.yandex.net")
                     .addConverterFactory(GsonConverterFactory.create(gson)).build();
        this.dbManager = dbManager;
        options.setCreate(true);
        Log.v(TAG, "Model constructor");
        getAvailableLanguagesImpl();

//        try {
//            db = dbManager.openDatabase(dbName, options);
//        } catch (CouchbaseLiteException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private String translatorAPIKey = "trnsl.1.1.20170409T053743Z.a2ebbf85a4d6a145.8224acd746d94e53a5e529a24f3d52c26c7f5d20";
    @Override
    public boolean saveData() {
        return false;
    }

    @Override
    public void getData() {

    }

    @Override
    public SupportLanguages getAvailableLanguages() {
        return supportLanguages;
    }

    public void getAvailableLanguagesImpl() {
        SupportLanguagesService languages = httpSender.create(SupportLanguagesService.class);
        Call<SupportLanguages> call = languages.getSupportLanguages(translatorAPIKey, originalLanguage);

        try {
            Response<SupportLanguages> supportLanguagesResponse = call.execute();
            if (!supportLanguagesResponse.isSuccessful()) {
                Log.v(TAG, "Server is not available or user reached the limit of requests");
                return;
            }

            supportLanguages = supportLanguagesResponse.body();
            Log.v(TAG, "Support languages successfully downloaded");
        } catch (IOException e) {
            Log.v(TAG, "Failure request", e);
        }
    }

    public String getTranslation() {
        return userLastTranslation;
    }

    public void requestTranslation(final String userString) {
        TranslatorService translatorService = httpSender.create(TranslatorService.class);
        Call<TranslateResponse> translation = translatorService.translate(translatorAPIKey,
                                                                          originalLanguage + '-' + languageOfTranslaton,
                                                                          userString);
        translation.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "Server is not available or user reached the limit of requests");
                    System.out.println(response.code() + response.message());
                    System.out.println(originalLanguage + '-' + languageOfTranslaton);
                    return;
                }

                // TODO: Handle other fields in answer
                userLastTranslation = response.body().getTranslatedText().get(0);
                Log.v(TAG, "String [" + userString + "] successfully trasnalted");
                notifyObservers();
            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {
                Log.w(TAG, "Failure request", t);
            }
        });
    }

    @Override
    public void setOriginalLanguage(String language) {
        for (TranslateDirection currentLanguage : supportLanguages.getDirections()) {
            if (currentLanguage.getLanguageName().equals(language)) {
                originalLanguage = currentLanguage.getLanguageUI();
                break;
            }
        }
    }

    @Override
    public void setLanguageOfTranslation(String language) {
        for (TranslateDirection currentLanguage : supportLanguages.getDirections()) {
            if (currentLanguage.getLanguageName().equals(language)) {
                languageOfTranslaton = currentLanguage.getLanguageUI();
                break;
            }
        }
    }

    @Override
    public void subscribeObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer currentObserver : observers)
            currentObserver.update(userLastTranslation);
    }
}
