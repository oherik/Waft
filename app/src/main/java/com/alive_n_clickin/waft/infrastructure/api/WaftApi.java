package com.alive_n_clickin.waft.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.waft.Config;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonFlag;
import com.alive_n_clickin.waft.infrastructure.api.response.Response;
import com.alive_n_clickin.waft.util.LogUtils;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A concrete implementation of IWaftApi.
 *
 * @since 1.0
 */
class WaftApi implements IWaftApi {
    private final String LOG_TAG = LogUtils.getLogTag(this);

    private static final String BASE_URL = Config.WAFT_URL;

    @Override
    public List<JsonFlag> getFlagsForJourney(String journeyId) throws ConnectionException {
        Response response = sendGet("/flags/" + journeyId);

        try {
            return JsonJavaConverter.toJavaList(response.getBody(), JsonFlag[].class);
        } catch (JsonSyntaxException e) {
            Log.e(LOG_TAG, "Error parsing JSON", e);
            throw new ConnectionException("Error parsing response from server", e);
        }
    }

    @Override
    public boolean addFlag(String dgw, String journeyId, int flagTypeId, String comment, Date createdTime)
            throws ConnectionException {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("flagType", flagTypeId + ""));
        parameters.add(new Parameter("comment", comment));
        parameters.add(new Parameter("time", createdTime.getTime() + ""));
        parameters.add(new Parameter("dgw", dgw));
        parameters.add(new Parameter("journeyID", journeyId));

        Response response = sendPost("/flags", parameters);

        return response.wasRequestSuccessful();
    }

    @Override
    public boolean deleteFlag(String id) throws ConnectionException {
        Response response = sendDelete("/flags/delete/" + id);
        return response.wasRequestSuccessful();
    }

    private static String buildUrl(String query) {
        return BASE_URL + query;
    }

    private static Response sendGet(String query) throws ConnectionException {
        String url = buildUrl(query);
        return ApiConnection.get(url);
    }

    private static Response sendPost(String query, List<Parameter> parameters) throws ConnectionException {
        String url = buildUrl(query);
        return ApiConnection.post(url, parameters);
    }

    private static Response sendDelete(String query) throws ConnectionException {
        String url = buildUrl(query);
        return ApiConnection.delete(url);
    }
}
