package ru.nsu.arturbarsegyan.yatranslator.model;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.network.SupportLanguagesService;

public class ModelImpl implements Model {
    private Retrofit httpSender = new Retrofit.Builder().baseUrl("https://translate.yandex.net").build();
    private final String dbName = "yaTranslatorDB";
    private DatabaseOptions options = new DatabaseOptions();
    private Database db = null;

    private String originalLanguage = "ru";
    private String languageOfTranslaton = "en";

    public ModelImpl() {
        getAvailableLanguages();
        options.setCreate(true);

        Manager manager = null;
        try {
            manager = new Manager(new AndroidContext(), Manager.DEFAULT_OPTIONS);
            db = manager.openDatabase(dbName, options);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (CouchbaseLiteException e) {
            System.out.println(e.getMessage());
        }
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
    public List<String> getAvailableLanguages() {
        SupportLanguagesService languages = httpSender.create(SupportLanguagesService.class);
        Call<List<SupportLanguages>> call = languages.getSupportLanguages(translatorAPIKey, originalLanguage);
        call.enqueue(new Callback<List<SupportLanguages>>() {
            @Override
            public void onResponse(Call<List<SupportLanguages>> call, Response<List<SupportLanguages>> response) {
                // TODO
            }

            @Override
            public void onFailure(Call<List<SupportLanguages>> call, Throwable t) {

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
