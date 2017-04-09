package ru.nsu.arturbarsegyan.yatranslator.model.dto;

public class LanguageDetect {
    @SerializedName("code")
    int code;
    @SerializedName("lang")
    String detectLanguage;
}
