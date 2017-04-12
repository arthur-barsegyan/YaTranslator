package ru.nsu.arturbarsegyan.yatranslator;

public interface Observable {
    void subscribeObserver(Observer observer);
    void unsubscribeObserver(Observer observer);
    void notifyObservers();
}
