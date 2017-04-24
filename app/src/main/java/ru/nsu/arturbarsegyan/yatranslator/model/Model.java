package ru.nsu.arturbarsegyan.yatranslator.model;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.Observable;
import ru.nsu.arturbarsegyan.yatranslator.TranslationData;


public interface Model extends Observable {
    void requestTranslation(final String userString);
    ArrayList<String> getAvailableLanguages();

    String getSourceLanguage();
    String getDestinationLanguage();

    void setSourceLanguage(String language);
    void setDestinationLanguage(String language);

    boolean addFavoriteTranslation(String favoriteTranslation);
    ArrayList<TranslationData> getFavoriteTranslations();

    boolean isServerAvailable();
}
