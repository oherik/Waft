package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alive_n_clickin.commutity.R;

/**
 * A fragment for the search function. Contains a edit text field for inputting a search term
 * and a list containing the results
 */
public class SearchFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView   = inflater.inflate(R.layout.search_fragment, container, false);
        return rootView;
    }
}
