package ru.nsu.arturbarsegyan.yatranslator;

import java.util.ArrayList;

public class ModelBundle {
    private boolean isTranslationUpdated = false;
    private boolean isLanguagesSupportUpdated = false;

    private String lastTranslationText;
    private ArrayList<String> supportLanguages;

    public ModelBundle(ArrayList<String> supportLanguages, String lastTranslationText) {
        if (supportLanguages != null) {
            setSupportLanguages(supportLanguages);
            isLanguagesSupportUpdated = true;
        }

        if (lastTranslationText != null) {
            setLastTranslationText(lastTranslationText);
            isTranslationUpdated = true;
        }
    }

    public ArrayList<String> getSupportLanguages() {
        return supportLanguages;
    }

    public void setSupportLanguages(ArrayList<String> supportLanguages) {
        this.supportLanguages = supportLanguages;
    }

    public String getLastTranslationText() {
        return lastTranslationText;
    }

    public void setLastTranslationText(String lastTranslationText) {
        this.lastTranslationText = lastTranslationText;
    }

    public boolean isTranslationUpdated() {
        return isTranslationUpdated;
    }

    public boolean isLanguagesSupportUpdated() {
        return isLanguagesSupportUpdated;
    }
}
