package ru.nsu.arturbarsegyan.yatranslator;

import java.util.List;

public class ModelBundle {
    private boolean isTranslationUpdated = false;
    private boolean isLanguagesSupportUpdated = false;

    private String lastTranslationText;
    private List<String> supportLanguages;

    public ModelBundle(List<String> supportLanguages, String lastTranslationText) {
        if (supportLanguages != null) {
            setSupportLanguages(supportLanguages);
            isLanguagesSupportUpdated = true;
        }

        if (lastTranslationText != null) {
            setLastTranslationText(lastTranslationText);
            isTranslationUpdated = true;
        }
    }

    public List<String> getSupportLanguages() {
        return supportLanguages;
    }

    public void setSupportLanguages(List<String> supportLanguages) {
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
