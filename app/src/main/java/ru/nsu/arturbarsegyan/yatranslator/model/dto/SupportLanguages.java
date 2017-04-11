package ru.nsu.arturbarsegyan.yatranslator.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportLanguages {
    public SupportLanguages() {}

    public List<TranslateDirection> getDirections() {
        return directions;
    }

    public void setDirections(List<TranslateDirection> directions) {
        this.directions = directions;
    }

    private List<TranslateDirection> directions;
}
