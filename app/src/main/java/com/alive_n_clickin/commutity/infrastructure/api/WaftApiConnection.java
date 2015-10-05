package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by OscarEvertsson on 05/10/15.
 */
class WaftApiConnection {
    private static final String BASE_URL_WAFT = "http://95.85.21.47/";

    public String sendGetToWaft(String path,String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return ApiConnection.getResponseFromHttpConnection(connection);
        } catch (IOException e) {
            String errorMessage = "Error connection to WAFT API";
            if (connection != null) {
                errorMessage = ApiConnection.readStream(connection.getErrorStream());
            }
            Log.e(LogUtils.getLogTag(this), errorMessage, e);
        }
        return null;
    }

    public int sendPostToWaft(String path,String postQuery) {
        return ApiConnection.post(BASE_URL_WAFT + path,postQuery);
    }
}
