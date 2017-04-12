package ru.nsu.arturbarsegyan.yatranslator.view;


import java.util.List;

public interface View {
    void setLanguageSpinner(List<String> supportedLanguages);
    void setTranslationViewText(String translation);
}
