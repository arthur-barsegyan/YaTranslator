package ru.nsu.arturbarsegyan.yatranslator.model;

import ru.nsu.arturbarsegyan.yatranslator.Observable;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;

public interface Model extends Observable {
    boolean saveData();
    void getData();

    void requestTranslation(final String userString);
    SupportLanguages getAvailableLanguages();

    void setOriginalLanguage(String language);
    void setLanguageOfTranslation(String langauge);

// Observer при обновлении должен обновлять сразу все данные или можно разделять наблюдателей по категориям?
// В данном случае наверное проще было бы обновлять все данные в наблюдателе сразу (сразу менять данные в GUI)
}
