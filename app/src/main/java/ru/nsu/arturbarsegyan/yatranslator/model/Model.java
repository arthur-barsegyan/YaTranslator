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

    void swapTranslationLanguages();

    boolean addFavoriteTranslation(String favoriteTranslation);
    ArrayList<TranslationData> getFavoriteTranslations();
// Observer при обновлении должен обновлять сразу все данные или можно разделять наблюдателей по категориям?
// В данном случае наверное проще было бы обновлять все данные в наблюдателе сразу (сразу менять данные в GUI)
}
