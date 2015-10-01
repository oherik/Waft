package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.R;

/**
 * A fragment for the search function. Contains a edit text field for inputting a search term
 * and a list containing the results
 */
public class SearchFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;
    SearchView search;
    ListView searchResults;
    int minumumSearchLength             = 2;

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
        search=(SearchView) rootView.findViewById(R.id.fragmentSearch);
        search.setQueryHint("Test om hint fungerar");
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Add a timer as well
                //search(search.getQuery().toString());
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= minumumSearchLength) {
                    searchResults.setVisibility(rootView.VISIBLE);
                    searchStops(search.getQuery().toString());
                } else {
                    searchResults.setVisibility(rootView.INVISIBLE);
                }


                return false;
            }

        });

        return rootView;
    }

    private void searchStops(String query){
        Toast.makeText(getActivity(), "lasdasdol", Toast.LENGTH_SHORT).show();
    }

}
