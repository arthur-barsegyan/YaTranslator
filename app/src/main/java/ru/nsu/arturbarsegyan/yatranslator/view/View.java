package ru.nsu.arturbarsegyan.yatranslator.view;

import java.util.List;

public interface View {
    void setLanguageList(List<String> languages);
    void setTranslationViewText(String translation);

    void setSourceLanguage(String source);
    void setDestinationLanguage(String destination);
}
