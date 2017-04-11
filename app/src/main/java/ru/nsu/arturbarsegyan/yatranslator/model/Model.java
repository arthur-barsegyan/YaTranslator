package ru.nsu.arturbarsegyan.yatranslator.model;

import java.util.List;

public interface Model {
    boolean saveData();
    void getData();

    void getAvailableLanguages();
    void setOriginalLanguage(String language);
    void setLanguageOfTranslation(String langauge);


}
