package ru.nsu.arturbarsegyan.yatranslator.model;


import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateDirection;

public class LangsDeserializer implements JsonDeserializer<SupportLanguages> {

    @Override
    public SupportLanguages deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        JsonObject langs = object.getAsJsonObject("langs");
        Set<Map.Entry<String, JsonElement>> langsDirectionEntries = langs.entrySet();

        List<TranslateDirection> supportLanguagesList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> currentDirection : langsDirectionEntries) {
            TranslateDirection current = new TranslateDirection();
            current.setLanguageUI(currentDirection.getKey());
            current.setLanguageName(currentDirection.getValue().getAsString());
            supportLanguagesList.add(current);
        }

        SupportLanguages supportDirections = new SupportLanguages();
        supportDirections.setDirections(supportLanguagesList);
        return supportDirections;
    }
}
