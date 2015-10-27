package com.alive_n_clickin.commutity.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.commutity.infrastructure.api.response.Response;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * For connecting to different API:s. Allows abstraction by letting us send only the query parameters
 * to the API we want, not bothering about authentication and the likes. ApiConnection is package
 * private: no need for layers higher up to use it directly!
 *
 * @since 0.1
 */
class ApiConnection {
    private final static String LOG_TAG = LogUtils.getLogTag(ApiConnection.class);

    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * Returns the response of a get request to an url without any parameters.
     *
     * @param url the url to send a get request to.
     * @return the response of the query.
     * @throws ConnectionException if there is any error while establishing the connection or
     * reading from it.
     */
    static Response get(URL url) throws ConnectionException {
        return get(url, new ArrayList<Parameter>());
    }

    /**
     * Returns the response of a get request to an url with the specified parameters.
     *
     * @param url the url to send a get request to.
     * @param parameters a list of parameters.
     * @return the response of the query.
     * @throws ConnectionException if there is any error while establishing the connection to the
     * server.
     */
    static Response get(URL url, List<Parameter> parameters) throws ConnectionException {
        int status;
        String body;

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (parameters != null) {
                for (Parameter parameter : parameters) {
                    connection.setRequestProperty(parameter.getKey(), parameter.getValue());
                }
            }

            connection.connect();

            inputStream = connection.getInputStream();

            status = connection.getResponseCode();
            body = readStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error opening or reading from HTTPUrlConnection", e);
            throw new ConnectionException("Error opening or reading from HTTPUrlConnection", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing HTTP input stream", e);
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        return new Response(status, body);
    }

    /**
     * Posts a http request to the server
     * @param url The full address for the location where to send the request
     * @param query The query to post in the body of the request
     * @return The response code from the server, or -1 if the request couldn't be sent
     */
    static int post(URL url,String query) {
        try {
            //Convert the parameters to UTF-8
            byte[] bodyPostData     = query.getBytes(StandardCharsets.UTF_8);
            int bodyPostDataLength  = bodyPostData.length;

            /* Creates a new URL connection, using HTTP. HTTPS could be implemented in the future, 
            but is at the moment not supported by the server.

            doOutput: true, since we want to send to the server

            setInstanceFollowRedirects: false, since the server shouldn't throw any Error 301's. It
            could be set to true if we want to follow any possible redirects.

            The setRequest methods specify which kind of message that's being sent to the server. In
            this case it's a POST request, using the default internet media type
            x-www-form-urlencoded (which uses key-value pairs, with ampersands to separate the pairs). 
            The request should be encoded using UTF-8, although both this and the media type can be 
            changed if needed (i.e. the server changes how it handles the requests). The length of the 
            request is also set here. 

            setUsesCache(false) forces Java to reload the file.
            */
            HttpURLConnection serverConnection = (HttpURLConnection) url.openConnection();
            serverConnection.setDoOutput(true);
            serverConnection.setInstanceFollowRedirects(false);
            serverConnection.setRequestMethod("POST");
            serverConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
            serverConnection.setRequestProperty("charset", CHARSET);
            serverConnection.setRequestProperty("Content-Length", Integer.toString(bodyPostDataLength));
            serverConnection.setUseCaches(false);

            //Send data
            DataOutputStream serverOutput = new DataOutputStream(serverConnection.getOutputStream());
            serverOutput.write(bodyPostData);

            //Log response code
            int status = serverConnection.getResponseCode();
            Log.v(LOG_TAG, "Response " + status);

            return status;
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Invalid URL. Current URL input was " + url.toString() +
                    ". Error message: " + e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Server connection error. Error message: " + e);
        }
        return -1; //Could not send request
    }

    static int delete(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("DELETE");
            connection.connect();

            int status = connection.getResponseCode();
            return status;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Server connection error. Error message: " + e);
        }
        return -1; //Could not send request

    }

    /**
     * Reads the content of any InputStream and returns it as a String.
     * @param inputStream
     * @return
     */
    static String readStream(InputStream inputStream) {

        Scanner sc = new Scanner(inputStream);
        //By setting delimiter \A (which marks the beginning of the file) the Scanner read the whole file.
        sc.useDelimiter("\\A");
        return sc.hasNext() ? sc.next() : "";
    }
}
