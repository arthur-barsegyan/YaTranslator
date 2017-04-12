package ru.nsu.arturbarsegyan.yatranslator.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResponse {
    public TranslateResponse() {}

    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String translateDirection;
    @SerializedName("text")
    private List<String> translatedText;

    public List<String> getTranslatedText() {
        return translatedText;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTranslateDirection() {
        return translateDirection;
    }

    public void setTranslateDirection(String translateDirection) {
        this.translateDirection = translateDirection;
    }

    public void setTranslatedText(List<String> translatedText) {
        this.translatedText = translatedText;
    }
}
