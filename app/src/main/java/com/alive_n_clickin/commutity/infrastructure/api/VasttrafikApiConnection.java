package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for the Vasttrafik API, which is then passed along to {@link ApiConnection}.
 *
 * The class is not meant to be instantiated directly. It's only used within {@link VasttrafikAdapter} to remove coupling.
 * @since 0.1
 */
class VasttrafikApiConnection {
    private static final String BASE_URL_VASTTRAFIK =
            "http://api.vasttrafik.se/bin/rest.exe/v1/";
    private static final String API_KEY = "<YOUR API KEY>";
    private final String LOG_TAG = LogUtils.getLogTag(this);

    /**
     * Send a query to Vasttrafik. It will be appended to the base url and sent using the credentials of the team
     * The query must be well formed.
     * @param query the query to be appended. Leave out the '?' at the beginning
     * @param path the path to be appended to the base url.
     * @throws MalformedURLException if the query parameter is invalid.
     * @retur
     */
    public String sendGetToVasttrafik(String path,String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_VASTTRAFIK).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.encodedQuery(query);
        uriBuilder.appendQueryParameter("authKey", API_KEY);
        uriBuilder.appendQueryParameter("format","json");

        Uri uri = uriBuilder.build();

        URL url = null;
        try {
            url = new URL(uri.toString());
            return ApiConnection.getResponseFromHttpConnection(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Couldn't build url with:" + uri.toString());
        }
        return null;
    }
}
