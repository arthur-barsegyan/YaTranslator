package ru.nsu.arturbarsegyan.yatranslator;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nsu.arturbarsegyan.yatranslator.model.dto.Language;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;

public class DBManager implements DataManager {
    private static final String TAG = DBManager.class.getSimpleName();

    private final String dbName = "yatranslatordb";
    private final String dbLanguagesDocument = "languages";
    private final String dbFavoriteTranslations = "favorite_translations";
    private DatabaseOptions options = new DatabaseOptions();
    private Manager dbManager;
    private Database db = null;
    private Document languages;

    //boolean isDBWorking = false;

    // TODO: [IMPORTANT] Read about Loaders
    public DBManager(AndroidContext context) {
        try {
            this.dbManager = new Manager(context, Manager.DEFAULT_OPTIONS);
            options.setCreate(true);

            db = dbManager.getDatabase(dbName);
            Log.v(TAG, "DB successfully opened");
        } catch (CouchbaseLiteException e) {
            Log.w(TAG, "DB init problems! Can't create/load DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getTranslationLanguages() {
        languages = db.getDocument(dbLanguagesDocument);
        Map<String, Object> languagesMap = languages.getProperties();
        if (languagesMap == null)
            return null;

        Map<String, String> supportLanguages = new HashMap<>();

        for (Map.Entry<String, Object> currentLanguage : languagesMap.entrySet()) {
            supportLanguages.put(currentLanguage.getKey(), (String) currentLanguage.getValue());
        }

        return supportLanguages;
    }

    /* Don't get delta changes, but copy all the objects again,
       so as not to mislead the user, if suddenly support for some languages
       will be canceled */
    @Override
    public boolean updateTranslationLanguages(Map<String, String> languages) {
        try {
            Document document = db.getDocument(dbLanguagesDocument);
            Map<String, Object> langsMap = new HashMap<>();

            for (Map.Entry<String, String> currentLanguage : languages.entrySet()) {
                langsMap.put(currentLanguage.getKey(), currentLanguage.getKey());
            }

            document.putProperties(langsMap);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Problems with writing translation languages in DB");
            return false;
        }

        return true;
    }

    /* Creating a special key for every translation:
        hash(Original text + Source Lang + '-' + Dest Lang) */
    @Override
    public boolean addFavoriteTranslation(TranslationData translationData) {
        try {
            Document document = db.getDocument(dbFavoriteTranslations);
            Map<String, Object> favoriteTranslations = new HashMap<>();
            if (document.getProperties() != null)
                favoriteTranslations.putAll(document.getProperties());

            String currentKey = translationData.getOriginalText() + translationData.getSrcLang() + '-'
                                                                  + translationData.getDstLang();
            favoriteTranslations.put(currentKey, translationData);
            document.putProperties(favoriteTranslations);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Problems with saving favorite translation!");
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<TranslationData> getFavoriteTranslations() {
        //try {
            Document document = db.getDocument(dbFavoriteTranslations);
            Map<String, Object> favoriteTranslation = document.getProperties();
            ArrayList<TranslationData> favoriteTranslationList = new ArrayList<>();

            if (favoriteTranslation == null)
                return favoriteTranslationList;

            for (Map.Entry<String, Object> currentTranslation : favoriteTranslation.entrySet()) {
                try {
                    favoriteTranslationList.add((TranslationData) currentTranslation.getValue());
                } catch (ClassCastException e) {}
            }

            return favoriteTranslationList;
        //}
        //return null;
    }

    @Override
    public boolean addTranslationInHistory(TranslationData translationData) {
        return false;
    }

    @Override
    public boolean updateUserSetting() {
        return false;
    }
}
