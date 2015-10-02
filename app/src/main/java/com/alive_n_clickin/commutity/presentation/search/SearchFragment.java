package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IVasttrafikAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;
import com.alive_n_clickin.commutity.presentation.main.MainActivity;
import com.alive_n_clickin.commutity.presentation.main.MainFragment;

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
    IVasttrafikAdapter vAdapter;
    SearchResultAdapter resultAdapter;

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vAdapter = ApiAdapterFactory.createVasttrafikAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView   = inflater.inflate(R.layout.search_fragment, container, false);
        search=(SearchView) rootView.findViewById(R.id.fragmentSearch);
        search.setQueryHint("Test om hint fungerar");
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);

        //Add adapter
        List<Stop> emptyStops = new ArrayList();
        resultAdapter = new SearchResultAdapter(getActivity(),emptyStops);
        searchResults.setAdapter(resultAdapter);

        //Add click listener on the adapter
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The user clicked on an entry
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getActivity().getApplicationContext();
                Stop stop = resultAdapter.getItem(i);

                //Send back the stop to the main view
                setMainStop(stop);

                //Close this fragment
                switchToMainFragment(stop);
            }
        });

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
                if (newText != null && !newText.isEmpty()) {
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

    private void setMainStop(Stop stop){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setCurrentStop(stop);

    }

    /**
     * Switch view to the main fragment
     */
    private void switchToMainFragment(Stop stop){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(Intent.EXTRA_RETURN_RESULT, stop);
        mainFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
