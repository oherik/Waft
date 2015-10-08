package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;
import com.alive_n_clickin.commutity.presentation.main.MainActivity;
import com.alive_n_clickin.commutity.presentation.main.MainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment for the search function. Contains a edit text field for inputting a search term
 * and a list containing the results
 * @since 0.1
 */
public class SearchFragment extends Fragment {
    SearchView search;
    ListView searchResults;
    IManager manager;
    SearchResultAdapter resultAdapter;

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = ((MyApplication) getActivity().getApplicationContext()).getManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView   = inflater.inflate(R.layout.search_fragment, container, false);

        //Initialize the search window
        search=(SearchView) rootView.findViewById(R.id.fragmentSearch);
        search.setQueryHint(getString(R.string.stop_search_hint));
        search.setIconified(false);

        //Add adapter to the result view
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);
        List<Stop> emptyStops = new ArrayList();
        resultAdapter = new SearchResultAdapter(getActivity(),emptyStops);
        searchResults.setAdapter(resultAdapter);

        //Add click listener on the adapter
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The user clicked on an entry
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Stop stop = resultAdapter.getItem(i);

                //Send back the stop to the main view
                setMainStop(stop);

                //Close this fragment
                switchToMainFragment();
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

    /**
     * Sets the current stop in the activity
     * @param stop The selected stop
     */
    private void setMainStop(Stop stop){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setCurrentStop(stop);

    }

    /**
     * Switch view to the main fragment by replacing which fragment that will be in the main
     * activity's container
     */
    private void switchToMainFragment(){
        MainFragment mainFragment = new MainFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //Hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Starts the async task for getting results based on the query
     * @param query The stop search query
     */
    private void searchStops(String query){
        SearchStopTask task = new SearchStopTask();
        task.execute(query);
    }

    /**
     * An async class calling the api helper for receiving result based on a search query
     */
    public class SearchStopTask extends AsyncTask<String, Void, List<Stop>> {
        @Override
        protected List<Stop> doInBackground(String... params) {
            try {
                return manager.searchForStops(params[0]);
            }catch(NullPointerException e){
                Log.e(LOG_TAG, e.getStackTrace()+"");
            }
            //No results found, return null
            return null;
        }
        @Override
        protected void onPostExecute(List<Stop> result){
            displayResults(result);
        }
    }

    /**
     * Clears the result view and display the new results
     * @param stops The results of the search
     */
    private void displayResults(List<Stop> stops){
        if(stops!=null) {
            resultAdapter.clear();
            resultAdapter.addAll(stops);
        }
    }
}
