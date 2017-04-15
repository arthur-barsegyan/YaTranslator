package ru.nsu.arturbarsegyan.yatranslator.view;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.R;
import ru.nsu.arturbarsegyan.yatranslator.presenter.Presenter;
import ru.nsu.arturbarsegyan.yatranslator.presenter.PresenterBundle;

public class MainActivity extends AppCompatActivity implements View {
    private String TAG = MainActivity.class.getSimpleName();
    private Presenter presenter;
    private TextView mTextMessage;

    ArrayAdapter<String> spinnerAdapter;
    private Spinner srcLanguage;
    private Spinner dstLanguage;
    private List<String> languageDirections = null;

    // TODO: Change to TextView
    private EditText translationView;
    private EditText userTextInputView;
    private Button initTranslationButton;
    private Button changeTranslationDirection;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }

            return false;
        }

    };

    private AdapterView.OnItemSelectedListener selectedSourceLangListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
            presenter.setSourceLanguage(languageDirections.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private AdapterView.OnItemSelectedListener selectedDestinationLangListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
            presenter.setDestinationLanguage(languageDirections.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private android.view.View.OnClickListener translationInitListener = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            Log.v(TAG, "User want to translate [" + userTextInputView.getText().toString() + "]");
            if (!userTextInputView.getText().toString().isEmpty())
                presenter.getTranslation(userTextInputView.getText().toString());
        }
    };

    private android.view.View.OnClickListener changeTranslationDirectionListener = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            Log.v(TAG, "Change language translation");
            presenter.swapTranslationLanguages();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        srcLanguage = (Spinner) findViewById(R.id.srcLanguage);
        dstLanguage = (Spinner) findViewById(R.id.dstLanguage);
        srcLanguage.setOnItemSelectedListener(selectedSourceLangListener);
        dstLanguage.setOnItemSelectedListener(selectedDestinationLangListener);

        translationView = (EditText) findViewById(R.id.editText3);
        userTextInputView = (EditText) findViewById(R.id.editText);

        initTranslationButton = (Button) findViewById(R.id.button);
        initTranslationButton.setOnClickListener(translationInitListener);

        changeTranslationDirection = (Button) findViewById(R.id.changeDirections);
        changeTranslationDirection.setOnClickListener(changeTranslationDirectionListener);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        PresenterBundle presenterBundle = new PresenterBundle(this, getApplicationContext());
        presenter = new Presenter(presenterBundle);
        presenter.addView(this);
    }

    @Override
    public void setLanguageList(List<String> supportedLanguages) {
        if (languageDirections == null) {
            languageDirections = supportedLanguages;
            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                                                      languageDirections);
            srcLanguage.setAdapter(spinnerAdapter);
            dstLanguage.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    public void setTranslationViewText(String translation) {
        translationView.setText(translation);
    }

    @Override
    public void setSourceLanguage(String source) {
        srcLanguage.setSelection(spinnerAdapter.getPosition(source));
    }

    @Override
    public void setDestinationLanguage(String destination) {
        dstLanguage.setSelection(spinnerAdapter.getPosition(destination));
    }
}