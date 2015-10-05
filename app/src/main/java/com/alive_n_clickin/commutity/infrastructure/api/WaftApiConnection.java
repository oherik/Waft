package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.net.MalformedURLException;
import java.net.URL;


class WaftApiConnection {
    private static final String BASE_URL_WAFT = "http://95.85.21.47/";
    private final String LOG_TAG = LogUtils.getLogTag(this);

    /**
     * Send a query to Waft. It will be appended to the base url.
     * The query must be well formed.
     * @param path the path to append for the base url
     * @param query the query to append, leave out ? in the beginning
     * @return
     */
    public String sendGetToWaft(String path,String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        try {
            URL url = new URL(uri.toString());
            return ApiConnection.getResponseFromHttpConnection(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Couldn't create url in: sendGetToWaft with the final uri: " + uri.toString());
        }
        return null;
    }

    public int sendPostToWaft(String path,String postQuery) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        Uri uri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Couldn't create url in: sendPostToWaft with the final uri: " + uri.toString());
        }

        if(url != null){
            return ApiConnection.post(url,postQuery);
        }
        return -1;
    }
}