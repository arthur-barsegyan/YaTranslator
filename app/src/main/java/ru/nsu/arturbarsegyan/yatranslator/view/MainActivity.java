package ru.nsu.arturbarsegyan.yatranslator.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.R;
import ru.nsu.arturbarsegyan.yatranslator.presenter.Presenter;
import ru.nsu.arturbarsegyan.yatranslator.presenter.PresenterBundle;

public class MainActivity extends AppCompatActivity implements View, TranslatorFragment.OnTranslatorInteractionListener {
    private String TAG = MainActivity.class.getSimpleName();
    private Presenter presenter;

    TranslatorFragment translator;

    // TODO: Store current showing fragment in backstack
    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.container, fragment)
                                   .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeTab = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_favorites:
                    //transaction.addToBackStack(null);
                    addFragment(new ItemFragment());
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Why I should using this
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(changeTab);

        presenter = (Presenter) getLastCustomNonConfigurationInstance();
        if (null == presenter)
            initPresenter(this);
        else
            presenter.addView(this);

        // TODO: We should get current active fragment and show it
        TranslatorFragment translator = (TranslatorFragment) getSupportFragmentManager().findFragmentById(R.layout.fragment_translator);
        if (translator == null) {
            translator = TranslatorFragment.newInstance(presenter.getSupportedLangs());
            addFragment(translator);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    void initPresenter(View view) {
        PresenterBundle presenterBundle = new PresenterBundle(view, getApplicationContext());
        presenter = new Presenter(presenterBundle);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void hideKeyboard(android.view.View focusedView) {
        if (focusedView != null) {
            focusedView.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            Log.v(TAG, "Hiding keyboard");
        }
    }

    @Override
    public void setLanguageList(List<String> languages) {

    }

    @Override
    public void setTranslationText(String translation) {
        translator.setTranslationViewText(translation);
    }

    @Override
    public void setSourceLanguage(String source) {

    }

    @Override
    public void setDestinationLanguage(String destination) {

    }

    @Override
    public void getTranslation(String srcLang, String dstLang, String userText) {
        presenter.setSourceLanguage(srcLang);
        presenter.setDestinationLanguage(dstLang);
        presenter.getTranslation(userText);
    }

    @Override
    public void addFavoriteTranslation(String userText) {
        // TODO: Implement it
    }

    @Override
    public void voiceText(String textLang, String userText) {
        // TODO: Implement it
    }
}