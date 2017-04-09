package ru.nsu.arturbarsegyan.yatranslator.model.dto;


public class TranslateResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String translateDirection;
    @SerializedName("text")
    private String translatedText;

}
