package ru.nsu.arturbarsegyan.yatranslator.model.dto;


public class TranslateDirection {
    public TranslateDirection() {}

    public String getLanguageUI() {
        return languageUI;
    }

    public void setLanguageUI(String languageUI) {
        this.languageUI = languageUI;
    }

    private String languageUI;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    private String languageName;
}
