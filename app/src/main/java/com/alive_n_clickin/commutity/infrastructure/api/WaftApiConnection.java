package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for our backend, which is then passed along to {@link ApiConnection}.
 *
 * The class is not meant to be instantiated directly. It's only used within {@link WaftAdapter} to remove coupling.
 *
 * @since 0.2
 */
class WaftApiConnection {
    private static final String BASE_URL_WAFT = "http://95.85.21.47/";
    private static final String LOG_TAG = LogUtils.getLogTag(WaftApiConnection.class);

    /**
     * Send a query to Waft. It will be appended to the base url.
     * The query must be well formed.
     * @param path the path to append for the base url, you should not add the '/' sign in front of your path.
     * @param secondPath the query to append, leave out ? in the beginning
     * @return a json string if successful otherwise null.
     */
    public String sendGetToWaft(String path,String secondPath) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.appendPath(secondPath);
        Uri uri = uriBuilder.build();

        try {
            URL url = new URL(uri.toString());
            return ApiConnection.getResponseFromHttpConnection(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Couldn't create url in: sendGetToWaft with the final uri: " + uri.toString());
        }
        return null;
    }

    /**
     * The query must be well formed.
     * @param path the path to be appended to the base url, you should not add the '/' sign in front of your path.
     * @param postQuery the query to append, leave out ? in the beginning
     * @return the status code from the response or -1 if unsuccessful
     */
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

        if (url != null) {
            return ApiConnection.post(url,postQuery);
        }
        return -1;
    }
}
