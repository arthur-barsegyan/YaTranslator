package ru.nsu.arturbarsegyan.yatranslator.view;

import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.presenter.Presenter;

public interface View {
    //void setLanguageList(List<String> languages);
    void setTranslationText(String translation);

    void setSourceLanguage(String source);
    void setDestinationLanguage(String destination);

    void setPresenter(Presenter presenter);

    void showSaveErrorMessage(String errorMsg);
    void showServerUnavailableState();
}
