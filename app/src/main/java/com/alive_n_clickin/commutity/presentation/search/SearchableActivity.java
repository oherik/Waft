package com.alive_n_clickin.commutity.presentation.search;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alive_n_clickin.commutity.R;

import java.util.ArrayList;

/**
 * This activity class handles the searches performed in the app, e.g. when the user searches for
 * bus stops. It presents the results as a list view.
 */
public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_search);

        //Get the query from the intent, if it's a search intent
        Intent queryIntent = getIntent();
        if(queryIntent.getAction().equals(Intent.ACTION_SEARCH)){
            String query = queryIntent.getExtras().getString(SearchManager.QUERY);
            searchStops(query);
        }
    }

    /**
     * Searches for bus and tram stops based on the inputted query.
     * @param query The (user submitted) search query
     * @return A list of all stops that are related to the query
     */
    private ArrayList<String> searchStops(String query){
        //TODO call the api helper and perform a search
        return null; //TODO placeholder
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
