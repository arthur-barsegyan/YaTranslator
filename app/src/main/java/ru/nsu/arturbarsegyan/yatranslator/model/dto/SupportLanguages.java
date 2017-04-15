package ru.nsu.arturbarsegyan.yatranslator.model.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SupportLanguages {
    @SerializedName("langs")
    private Map<String, String> supportedLanguages;

    public SupportLanguages() {}

    public Map<String, String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(Map<String, String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }
}
