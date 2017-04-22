package ru.nsu.arturbarsegyan.yatranslator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.nsu.arturbarsegyan.yatranslator.R;
import ru.nsu.arturbarsegyan.yatranslator.view.dummy.DummyContent;
import ru.nsu.arturbarsegyan.yatranslator.view.dummy.DummyContent.DummyItem;

// TODO: [IMPORTANT] Make this fragment specially for user favorite translations
public class UserActivityFragment extends Fragment {
    public interface OnUserActivityInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);

        /* Async method, because we can getting information from anywhere (example, network).
           In our case, we get information from DB */
        void getFavoriteTranslations();
        void showTranslation(String textLang, String favoriteTranslation);
        void removeFavorite(String favoriteTranslation);
    }

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnUserActivityInteractionListener listener;

    public UserActivityFragment() {

    }

    public static UserActivityFragment newInstance(int columnCount) {
        UserActivityFragment fragment = new UserActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, listener));
        }
        return view;
    }

    public void setFavoritesTranslations(ArrayList<String> favoritesTranslationsList) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (! (context instanceof OnUserActivityInteractionListener))
            throw new RuntimeException(context.toString()
                    + " must implement OnUserActivityInteractionListener");

        listener = (OnUserActivityInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
