package ru.nsu.arturbarsegyan.yatranslator;

import java.util.Map;

public interface DataManager {
    Map<String, String> getTranslationLanguages();
    // ...

    boolean updateTranslationLanguages(Map<String, String> languages);
    boolean addFavoriteTranslation(/* Translation class */);
    boolean addTranslationInHistory(/* Translation class */);
    boolean updateUserSetting(/* Setting class */);
}
