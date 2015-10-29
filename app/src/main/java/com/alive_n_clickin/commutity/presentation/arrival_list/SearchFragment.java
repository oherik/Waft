package com.alive_n_clickin.commutity.presentation.arrival_list;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.alive_n_clickin.commutity.application.CentralApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.IStop;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment for the search function. Contains a edit text field for inputting a search term
 * and a list containing the results.
 *
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
        manager = ((CentralApplication) getActivity().getApplicationContext()).getManager();
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
        List<IStop> emptyStops = new ArrayList();
        resultAdapter = new SearchResultAdapter(getActivity(),emptyStops);
        searchResults.setAdapter(resultAdapter);

        //Add click listener on the adapter
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The user clicked on an entry
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IStop stop = resultAdapter.getItem(i);

                //Send back the stop to the main view
                setMainStop(stop);

                //Close this fragment
                switchToMainFragment();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
    private void setMainStop(IStop stop) {
        ArrivalListActivity arrivalListActivity = (ArrivalListActivity) getActivity();
        arrivalListActivity.setCurrentStop(stop);

    }

    /**
     * Switch view to the main fragment by replacing which fragment that will be in the main
     * activity's container
     */
    private void switchToMainFragment() {
        ArrivalListFragment arrivalListFragment = new ArrivalListFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, arrivalListFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        //Hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Starts the async task for getting results based on the query
     * @param query The stop search query
     */
    private void searchStops(String query) {
        SearchStopTask task = new SearchStopTask();
        task.execute(query);
    }

    /**
     * An async class calling the api helper for receiving result based on a search query
     */
    public class SearchStopTask extends AsyncTask<String, Void, List<IStop>> {
        @Override
        protected List<IStop> doInBackground(String... params) {
            return manager.searchForStops(params[0]);
        }
        @Override
        protected void onPostExecute(List<IStop> result) {
            displayResults(result);
        }
    }

    /**
     * Clears the result view and display the new results
     * @param stops The results of the search
     */
    private void displayResults(List<IStop> stops) {
        resultAdapter.clear();
        resultAdapter.addAll(stops);
    }
}
