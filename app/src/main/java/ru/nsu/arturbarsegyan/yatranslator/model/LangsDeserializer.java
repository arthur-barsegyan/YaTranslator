package ru.nsu.arturbarsegyan.yatranslator.model;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.nsu.arturbarsegyan.yatranslator.model.dto.Language;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;

public class LangsDeserializer implements JsonDeserializer<SupportLanguages> {

    @Override
    public SupportLanguages deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        JsonObject langs = object.getAsJsonObject("langs");
        Set<Map.Entry<String, JsonElement>> langsDirectionEntries = langs.entrySet();

        Map<String, String> supportLanguagesMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> currentDirection : langsDirectionEntries) {
            supportLanguagesMap.put(currentDirection.getValue().getAsString(), currentDirection.getKey());
        }

        SupportLanguages supportDirections = new SupportLanguages();
        supportDirections.setSupportedLanguages(supportLanguagesMap);
        return supportDirections;
    }
}
