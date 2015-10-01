package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.HttpRequest;
import com.alive_n_clickin.commutity.infrastructure.Stop;
import com.alive_n_clickin.commutity.infrastructure.VasttrafikAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment for the search function. Contains a edit text field for inputting a search term
 * and a list containing the results
 */
public class SearchFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;
    SearchView search;
    ListView searchResults;
    VasttrafikAdapter vAdapter;
    SearchResultAdapter resultAdapter;

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vAdapter = new VasttrafikAdapter();
            }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView   = inflater.inflate(R.layout.search_fragment, container, false);
        search=(SearchView) rootView.findViewById(R.id.fragmentSearch);
        search.setQueryHint("Test om hint fungerar");
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);

        List<Stop> emptyStops = new ArrayList<Stop>();
        resultAdapter = new SearchResultAdapter(getActivity(),emptyStops);
        searchResults.setAdapter(resultAdapter);



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
                if (newText != null) {
              //      searchResults.setVisibility(rootView.VISIBLE);
                    searchStops(search.getQuery().toString());
                } else {
                //    searchResults.setVisibility(rootView.INVISIBLE);
                }


                return false;
            }

        });

        return rootView;
    }

    private void searchStops(String query){
        SearchStopTask task = new SearchStopTask();
        task.execute(query);
        Log.e(LOG_TAG, query);
    }


    public class SearchStopTask extends AsyncTask<String, Void, List<Stop>> {
        @Override
        protected List<Stop> doInBackground(String... params) {

            try {
                return vAdapter.getSearchStops(params[0]);
            }catch(NullPointerException e){
                Log.e(LOG_TAG, e.getStackTrace()+"");
            }

            return null;
        }
        @Override
        protected void onPostExecute(List<Stop> result){
            displayResults(result);
        }
    }
    private void displayResults(List<Stop> stops){
        if(stops!=null) {

            resultAdapter.clear();
            resultAdapter.addAll(stops);
        }

    }

}
