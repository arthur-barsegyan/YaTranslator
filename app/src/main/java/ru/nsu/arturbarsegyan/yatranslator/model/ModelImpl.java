package ru.nsu.arturbarsegyan.yatranslator.model;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateDirection;
import ru.nsu.arturbarsegyan.yatranslator.model.network.SupportLanguagesService;

public class ModelImpl implements Model {
    private static final String TAG = Model.class.getName();
    final Gson gson;
    private Retrofit httpSender;
    private final String dbName = "yaTranslatorDB";
    private DatabaseOptions options = new DatabaseOptions();
    private Manager dbManager;
    private Database db = null;

    private List<TranslateDirection> supportLanguagesList = null;
    private String originalLanguage = "ru";
    private String languageOfTranslaton = "en";

    public ModelImpl(Manager dbManager) {
        gson =  new GsonBuilder()
                .registerTypeAdapter(SupportLanguages.class, new LangsDeserializer())
                .create();
        httpSender = new Retrofit.Builder().baseUrl("https://translate.yandex.net")
                     .addConverterFactory(GsonConverterFactory.create(gson)).build();
        this.dbManager = dbManager;
        options.setCreate(true);
        Log.v(TAG, "Model constructor");
        getAvailableLanguages();

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
    public void getAvailableLanguages() {
        SupportLanguagesService languages = httpSender.create(SupportLanguagesService.class);
        Call<SupportLanguages> call = languages.getSupportLanguages(translatorAPIKey, originalLanguage);
        call.enqueue(new Callback<SupportLanguages>() {
            @Override
            public void onResponse(Call<SupportLanguages> call, Response<SupportLanguages> response) {
                if (!response.isSuccessful()) {
                    // fixme: Fix this message
                    Log.v(TAG, "Connection problem");
                    return;
                }

                supportLanguagesList = response.body().getDirections();
                Log.v(TAG, "Support Languages successfully downloaded");
            }

            @Override
            public void onFailure(Call<SupportLanguages> call, Throwable t) {
                Log.v(TAG, "Failure request", t);
            }
        });


    }

    @Override
    public void setOriginalLanguage(String language) {

    }

    @Override
    public void setLanguageOfTranslation(String language) {

    }
}
