package ru.nsu.arturbarsegyan.yatranslator.model.dto;

import com.google.gson.annotations.SerializedName;

public class LanguageDetect {
    public LanguageDetect() {}
    @SerializedName("code")
    int code;
    @SerializedName("lang")
    String detectLanguage;
}
