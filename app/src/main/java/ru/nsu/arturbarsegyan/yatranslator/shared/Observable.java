package ru.nsu.arturbarsegyan.yatranslator.shared;

import java.util.Map;

public interface Observable {
    void subscribeObserver(Observer observer);
    void unsubscribeObserver(Observer observer);
    void notifyObservers(Map<String, String> newSupportedLanguages, String userLastTranslation);
}
