package com.alive_n_clickin.commutity.presentation.search;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;

import java.util.ArrayList;

/**
 * This activity class handles the searches performed in the app, e.g. when the user searches for
 * bus stops. It presents the results as a list view.
 */
public class SearchableActivity extends ListActivity {

    private final String LOG_TAG = SearchableActivity.class.getSimpleName();

    private TextView stopTextView;
    private ListView stopResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_search);
       // stopTextView = (TextView) findViewById(R.id.search_text);
      //  stopResultView = (ListView) findViewById(R.id.list);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) findViewById(R.id.stopSearchView);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        parseIntent(getIntent());
    }

    /**
     * Madde so that a new activity isn't created every time
     * @param intent The submitted intent containing a search query
     */
    @Override
    protected void onNewIntent(Intent intent) {
        parseIntent(intent);
    }

    /**
     * Searches for bus and tram stops based on the inputted query.
     * @param query The (user submitted) search query
     * @return A list of all stops that are related to the query
     */
    private ArrayList<String> searchStops(String query){
        Log.e(LOG_TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA  " + query);
                //TODO call the api helper and perform a search
        return null; //TODO placeholder
    }

    /**
     * Decides what should happen with the passed intent (e.g. perform a search based on a query)
     * @param queryIntent    The submitted intent
     */

    private void parseIntent(Intent queryIntent) {
        //Get the query from the intent, if it's a search intent
        if (Intent.ACTION_SEARCH.equals(queryIntent.getAction())) {
            String query = queryIntent.getStringExtra(SearchManager.QUERY);
            searchStops(query);
        }
        //TODO handle when the user clicks on a stop
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stop_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
