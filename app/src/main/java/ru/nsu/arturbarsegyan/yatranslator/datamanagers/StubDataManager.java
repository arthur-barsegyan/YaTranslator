package ru.nsu.arturbarsegyan.yatranslator.datamanagers;


import java.util.ArrayList;
import java.util.Map;

import ru.nsu.arturbarsegyan.yatranslator.shared.TranslationData;

public class StubDataManager implements DataManager {

    @Override
    public Map<String, String> getTranslationLanguages() {
        return null;
    }

    @Override
    public boolean updateTranslationLanguages(Map<String, String> languages) {
        return true;
    }

    @Override
    public boolean addFavoriteTranslation(TranslationData translationData) {
        return true ;
    }

    @Override
    public ArrayList<TranslationData> getFavoriteTranslations() {
        return null;
    }

    @Override
    public boolean addTranslationInHistory(TranslationData translationData) {
        return true;
    }

    @Override
    public boolean updateUserSetting() {
        return true;
    }

    @Override
    public void saveData() {

    }
}
