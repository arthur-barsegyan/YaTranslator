package ru.nsu.arturbarsegyan.yatranslator;

import java.util.ArrayList;
import java.util.Map;

public interface DataManager {
    Map<String, String> getTranslationLanguages();
    // ...

    boolean updateTranslationLanguages(Map<String, String> languages);

    boolean addFavoriteTranslation(TranslationData translationData);
    ArrayList<TranslationData> getFavoriteTranslations();

    boolean addTranslationInHistory(TranslationData translationData);

    boolean updateUserSetting(/* Setting class */);
}
