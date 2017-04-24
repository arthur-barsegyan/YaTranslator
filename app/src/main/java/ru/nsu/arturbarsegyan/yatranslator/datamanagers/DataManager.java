package ru.nsu.arturbarsegyan.yatranslator.datamanagers;

import java.util.ArrayList;
import java.util.Map;

import ru.nsu.arturbarsegyan.yatranslator.shared.TranslationData;

public interface DataManager {
    Map<String, String> getTranslationLanguages();
    // ...

    boolean updateTranslationLanguages(Map<String, String> languages);

    boolean addFavoriteTranslation(TranslationData translationData);
    ArrayList<TranslationData> getFavoriteTranslations();

    boolean addTranslationInHistory(TranslationData translationData);

    boolean updateUserSetting(/* Setting class */);

    void saveData();
}
