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
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import ru.nsu.arturbarsegyan.yatranslator.R;
import ru.nsu.arturbarsegyan.yatranslator.shared.TranslationData;
import ru.nsu.arturbarsegyan.yatranslator.presenter.Presenter;
import ru.nsu.arturbarsegyan.yatranslator.presenter.PresenterBundle;
import ru.nsu.arturbarsegyan.yatranslator.view.dummy.DummyContent;

// TODO: [IMPORTANT] Reading more about PreferenceFragment
public class MainActivity extends AppCompatActivity implements View, TranslatorFragment.OnTranslatorInteractionListener,
                                                                UserActivityFragment.OnUserActivityInteractionListener{
    private String TAG = MainActivity.class.getSimpleName();
    private Presenter presenter;

    private final static int TRANSLATOR_ITEM_ID = 0;
    private final static int USERACTIVITY_ITEM_ID = 1;
    private final static int SETTINGS_ITEM_ID = 2;


    TranslatorFragment translator = null;
    UserActivityFragment userActivityFragment = null;

    private void replaceFragment(Fragment fragment, String fragmentTag) {
        getSupportFragmentManager().beginTransaction()
                                   .addToBackStack(null)
                                   .replace(R.id.layoutFrame, fragment, fragmentTag)
                                   .commit();
    }

    // TODO: Add handler for "Back" Button (addOnBackStackChangedListener())
    private BottomNavigationView.OnNavigationItemSelectedListener changeTab = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_translate:
                    replaceFragment(translator, "translator");
                    return true;
                case R.id.navigation_user_activity:
                    if (userActivityFragment == null) {
                        userActivityFragment = new UserActivityFragment();
                        replaceFragment(userActivityFragment, "userActivity");
                        return true;
                    }

                    replaceFragment(userActivityFragment, "userActivity");
                    return true;
            }

            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // TODO: Add information about current showed fragment in Bundle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        translator = (TranslatorFragment) getSupportFragmentManager().findFragmentByTag("translator");
        if (translator == null) {
            createTranslatorFragment();
        }

//        presenter.updateView();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.viewClosed();
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

    @Override
    public void showSaveErrorMessage(String errorMsg) {
        // TODO: [IMPORTANT] Implement this
    }

    private void createTranslatorFragment() {
        translator = TranslatorFragment.newInstance(presenter.getSupportedLangs());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layoutFrame, translator, "translator")
                .commit();
    }

    @Override
    public void showServerUnavailableState() {
//        if (translator == null)
//            createTranslatorFragment();
//        else
//            replaceFragment(translator, "translator");

        translator.showServerAvailableStatus(false);
    }

    public void hideKeyboard(android.view.View focusedView) {
        if (focusedView != null) {
            focusedView.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            Log.v(TAG, "Hiding keyboard");
        }

    }

    private void setNavigationItemSelected(int id) {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(id);
        menuItem.setChecked(true);
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
    public void setTranslatorFragmentOccurence() {
        setNavigationItemSelected(TRANSLATOR_ITEM_ID);
    }

    @Override
    public boolean getServerStatus() {
        return presenter.getServerStatus();
    }

    @Override
    public void setUserActivityFragmentOccurence() {
        setNavigationItemSelected(USERACTIVITY_ITEM_ID);
    }

    @Override
    public void getTranslation(String srcLang, String dstLang, String userText) {
        presenter.setSourceLanguage(srcLang);
        presenter.setDestinationLanguage(dstLang);
        presenter.getTranslation(userText);
    }

    @Override
    public void addFavoriteTranslation(String userText) {
        presenter.addFavoriteTranslation(userText);
    }

    @Override
    public ArrayList<TranslationData> getFavoriteTranslations() {
        return presenter.getFavoriteTranslations();
    }

    @Override
    public void voiceText(String textLang, String userText) {
        // TODO: Implement it
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void showTranslation(String textLang, String favoriteTranslation) {

    }

    @Override
    public void removeFavorite(String favoriteTranslation) {

    }
}