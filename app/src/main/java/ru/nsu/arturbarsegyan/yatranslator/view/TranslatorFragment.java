package ru.nsu.arturbarsegyan.yatranslator.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.arturbarsegyan.yatranslator.R;

public class TranslatorFragment extends Fragment {
    public interface OnTranslatorInteractionListener {
        void setTranslatorFragmentOccurence();

        void getTranslation(String srcLang, String dstLang, String userText);
        void addFavoriteTranslation(String userText);
        void voiceText(String textLang, String userText);
        void hideKeyboard(View view);
    }

    private final String TAG = TranslatorFragment.class.getSimpleName();
    private final static String langsKey = "langs";

    private final static String defaultSrcLang = "Русский";
    private final static String defaultDstLang = "Английский";

    ArrayAdapter<String> spinnerAdapter;
    private Spinner srcLanguageSpinner;
    private Spinner dstLanguageSpinner;
    private List<String> languageDirections = null;
    private String srcLanguage = defaultSrcLang;
    private String dstLanguage = defaultDstLang;

    private TextView translationView;
    private EditText userTextInputView;
    private Button changeTranslationDirection;

    private OnTranslatorInteractionListener listener;

    public TranslatorFragment() {

    }

    public static TranslatorFragment newInstance(ArrayList<String> languages) {
        TranslatorFragment fragment = new TranslatorFragment();

        Bundle bundle = new Bundle();
        for (String currentLang : languages)
            bundle.putStringArrayList(langsKey, languages);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (! (getActivity() instanceof OnTranslatorInteractionListener))
            throw new RuntimeException(context.toString()
                    + " must implement OnTranslatorInteractionListener");;

        listener = (OnTranslatorInteractionListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_translator, container, false);
        srcLanguageSpinner = (Spinner) view.findViewById(R.id.srcLanguage);
        dstLanguageSpinner = (Spinner) view.findViewById(R.id.dstLanguage);
        srcLanguageSpinner.setOnItemSelectedListener(selectedSourceLangListener);
        dstLanguageSpinner.setOnItemSelectedListener(selectedDestinationLangListener);

        if (bundle != null) {
            setLanguageList(bundle.getStringArrayList(langsKey));
            setDefaultLanguages();
        }

        translationView = (TextView) view.findViewById(R.id.translationView);
        //translationView.set
        userTextInputView = (EditText) view.findViewById(R.id.userInputArea);
        userTextInputView.setOnEditorActionListener(getTranslation);
        userTextInputView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        changeTranslationDirection = (Button) view.findViewById(R.id.changeDirections);
        changeTranslationDirection.setOnClickListener(changeTranslationDirectionListener);

        Button addToFavorites = (Button) view.findViewById(R.id.favoriteTranslation);
        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addFavoriteTranslation(userTextInputView.getText().toString());
            }
        });

        Button clearInput = (Button) view.findViewById(R.id.clearInput);
        clearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTextInputView.setText("");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setTranslatorFragmentOccurence();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Paused Translator!");
    }

    private AdapterView.OnItemSelectedListener selectedSourceLangListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
            setSourceLanguage(languageDirections.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private AdapterView.OnItemSelectedListener selectedDestinationLangListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
            setDestinationLanguage(languageDirections.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private android.view.View.OnClickListener changeTranslationDirectionListener = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            Log.v(TAG, "Change language translation");
            swapTranslationLanguages();
        }
    };

    // TODO: Maybe we should getting translation every moment when user change text?
    private TextView.OnEditorActionListener getTranslation = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView inputView, int actionId, KeyEvent event) {
            Log.v(TAG, "User want to translate [" + inputView.getText().toString() + "]");
            if (!inputView.getText().toString().isEmpty()) {
                listener.getTranslation(srcLanguage, dstLanguage, inputView.getText().toString());
                listener.hideKeyboard(inputView);
                return true;
            }

            return false;
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setLanguageList(List<String> supportedLanguages) {
        if (languageDirections == null) {
            languageDirections = supportedLanguages;
            spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                                                languageDirections);
        }

        spinnerAdapter.setDropDownViewResource(R.layout.language_custom_spinner);
        srcLanguageSpinner.setAdapter(spinnerAdapter);
        dstLanguageSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }

    public void setTranslationViewText(String translation) {
        translationView.setText(translation);
    }

    private void setSourceLanguage(String srcLanguage) {
        this.srcLanguage = srcLanguage;
    }

    private void setDestinationLanguage(String dstLanguage) {
        this.dstLanguage = dstLanguage;
    }

    private void swapTranslationLanguages() {
        String currentSrcLang = srcLanguage;
        setSourceLanguage(dstLanguage);
        setDestinationLanguage(currentSrcLang);

        srcLanguageSpinner.setSelection(spinnerAdapter.getPosition(srcLanguage));
        dstLanguageSpinner.setSelection(spinnerAdapter.getPosition(dstLanguage));
    }

    private void setDefaultLanguages() {
        if (spinnerAdapter.getPosition(defaultSrcLang) >= 0 && spinnerAdapter.getPosition(defaultDstLang) >= 0) {
            srcLanguageSpinner.setSelection(spinnerAdapter.getPosition(defaultSrcLang));
            dstLanguageSpinner.setSelection(spinnerAdapter.getPosition(defaultDstLang));
        }
    }
}