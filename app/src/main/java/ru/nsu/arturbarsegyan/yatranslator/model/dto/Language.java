package ru.nsu.arturbarsegyan.yatranslator.model.dto;


public class Language {
    private String languageName;
    private String languageUI;

    public Language() {}
    public Language(String name, String ui) {
        languageName = name;
        languageUI = ui;
    }

    public String getLanguageUI() {
        return languageUI;
    }

    public void setLanguageUI(String languageUI) {
        this.languageUI = languageUI;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

}
