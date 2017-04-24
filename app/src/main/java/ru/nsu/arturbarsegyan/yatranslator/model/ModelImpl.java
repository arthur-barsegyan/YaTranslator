package ru.nsu.arturbarsegyan.yatranslator.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import ru.nsu.arturbarsegyan.yatranslator.DataManager;
import ru.nsu.arturbarsegyan.yatranslator.ModelBundle;
import ru.nsu.arturbarsegyan.yatranslator.Observer;

import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import ru.nsu.arturbarsegyan.yatranslator.TranslationData;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateResponse;
import ru.nsu.arturbarsegyan.yatranslator.model.network.SupportLanguagesService;
import ru.nsu.arturbarsegyan.yatranslator.model.network.TranslatorService;

public class ModelImpl implements Model  {
    private static final String TAG = Model.class.getName();

    private Gson gson;
    private Retrofit httpSender;

    private DataManager dataManager;

    private Map<String, String> supportLanguages = null;
    private String sourceLanguage = "ru";
    private String destinationLanguage = "en";

    private String userLastTranslation = null;
    private Set<Observer> observers = new HashSet<>();

    private boolean isServerAvailable = true;

    public ModelImpl(DataManager dataManager) {
        Log.v(TAG, "Model constructor");
        gson = new GsonBuilder()
                .registerTypeAdapter(SupportLanguages.class, new LangsDeserializer())
                .create();
        httpSender = new Retrofit.Builder().baseUrl("https://translate.yandex.net")
                     .addConverterFactory(GsonConverterFactory.create(gson)).build();
        this.dataManager = dataManager;
    }

    private String translatorAPIKey = "trnsl.1.1.20170409T053743Z.a2ebbf85a4d6a145.8224acd746d94e53a5e529a24f3d52c26c7f5d20";

    // Assumed that Presenter doesn't need an abbreviation for the language
    @Override
    public ArrayList<String> getAvailableLanguages() {
        // TODO: Handle failed request
        if (supportLanguages == null)
            downloadAvailableLanguages(false);

        /* If we can't get support languages from server we getting it from DataManager
           (Backups for offline mode) */
        if (supportLanguages == null) {
            supportLanguages = dataManager.getTranslationLanguages();
            isServerAvailable = false;

            if (supportLanguages == null) {
                return null;
            }
        }

        ArrayList<String> supportedLanguagesList = new ArrayList<>();
        for (String currentLang : supportLanguages.keySet())
            supportedLanguagesList.add(currentLang);

        return supportedLanguagesList;
    }

    @Override
    public String getSourceLanguage() {
        return sourceLanguage;
    }

    @Override
    public String getDestinationLanguage() {
        return destinationLanguage;
    }

    public boolean isServerAvailable() {
        return isServerAvailable;
    }

    // TODO: [IMPORTANT] Create waiting timeout
    // TODO: Make async version of this task
    public void downloadAvailableLanguages(boolean notifyObserversMode) {
        SupportLanguagesService languages = httpSender.create(SupportLanguagesService.class);
        Call<SupportLanguages> call = languages.getSupportLanguages(translatorAPIKey, sourceLanguage);

        try {
            Response<SupportLanguages> supportLanguagesResponse = call.execute();
            if (!supportLanguagesResponse.isSuccessful()) {
                Log.v(TAG, "Server is not available or user reached the limit of requests");
                return;
            }

            supportLanguages = supportLanguagesResponse.body().getSupportedLanguages();
            Log.v(TAG, "Support languages successfully downloaded");
            dataManager.updateTranslationLanguages(supportLanguages);

            if (notifyObserversMode)
                notifyObservers(supportLanguages, null);
        } catch (IOException e) {
            Log.v(TAG, "Failure request", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "Receive empty response with supported languages!");
        }
    }

    public String getTranslation() {
        return userLastTranslation;
    }

    public void requestTranslation(final String userString) {
        TranslatorService translatorService = httpSender.create(TranslatorService.class);
        Call<TranslateResponse> translation = translatorService.translate(translatorAPIKey,
                                                                          sourceLanguage + '-' + destinationLanguage,
                                                                          userString);
        translation.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "Server is not available or user reached the limit of requests");
                    System.out.println(response.code() + response.message());
                    System.out.println(sourceLanguage + '-' + destinationLanguage);
                    return;
                }

                // TODO: Handle other fields in answer
                userLastTranslation = response.body().getTranslatedText().get(0);
                Log.v(TAG, "String [" + userString + "] successfully trasnalted");
                notifyObservers(null, userLastTranslation);
            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {
                Log.w(TAG, "Failure request", t);
            }
        });
    }

    @Override
    public void setSourceLanguage(String language) {
        sourceLanguage = supportLanguages.get(language);
    }

    @Override
    public void setDestinationLanguage(String language) {
        destinationLanguage = supportLanguages.get(language);
    }

    @Override
    public void swapTranslationLanguages() {
        String temp = sourceLanguage;
        sourceLanguage = destinationLanguage;
        destinationLanguage = temp;
    }

    // TODO: Make this task async
    @Override
    public boolean addFavoriteTranslation(String favoriteTranslation) {
        TranslationData favoriteTranslationData = new TranslationData(favoriteTranslation, userLastTranslation,
                                                                      sourceLanguage, destinationLanguage);
        return dataManager.addFavoriteTranslation(favoriteTranslationData);
    }

    @Override
    public ArrayList<TranslationData> getFavoriteTranslations() {
        return dataManager.getFavoriteTranslations();
    }

    @Override
    public void subscribeObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribeObserver(Observer observer) {
        observers.remove(observer);
    }

    // TODO: Change interface (Create several notification types)
    @Override
    public void notifyObservers(Map<String, String> newSupportedLanguages, String userLastTranslation) {
        for (Observer currentObserver : observers) {
            currentObserver.update(new ModelBundle(null, userLastTranslation));
        }
    }
}
