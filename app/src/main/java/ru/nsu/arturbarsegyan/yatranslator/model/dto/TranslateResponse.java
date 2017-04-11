package ru.nsu.arturbarsegyan.yatranslator.model.dto;


import com.google.gson.annotations.SerializedName;

public class TranslateResponse {
    public TranslateResponse() {}

    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String translateDirection;
    @SerializedName("text")
    private String translatedText;

}
