package com.alive_n_clickin.waft.infrastructure.api;

import com.alive_n_clickin.waft.infrastructure.api.response.JsonFlag;
import com.alive_n_clickin.waft.infrastructure.api.response.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A concrete implementation of IWaftApi.
 *
 * @since 1.0
 */
class WaftApi implements IWaftApi {
    private static final String BASE_URL = Config.WAFT_URL;

    @Override
    public List<JsonFlag> getFlagsForJourney(String journeyId) {
        Response response = sendGet("/flags/" + journeyId);

        if (response == null) {
            return new ArrayList<>();
        }

        return JsonJavaConverter.toJavaList(response.getBody(), JsonFlag[].class);
    }

    @Override
    public boolean addFlag(String dgw, String journeyId, int flagTypeId, String comment, Date createdTime) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("flagType", flagTypeId + ""));
        parameters.add(new Parameter("comment", comment));
        parameters.add(new Parameter("time", createdTime.getTime() + ""));
        parameters.add(new Parameter("dgw", dgw));
        parameters.add(new Parameter("journeyID", journeyId));

        Response response = sendPost("/flags", parameters);

        return response != null && response.wasRequestSuccessful();
    }

    @Override
    public boolean deleteFlag(String id) {
        Response response = sendDelete("/flags/delete/" + id);
        return response != null && response.wasRequestSuccessful();
    }

    private static String buildUrl(String query) {
        return BASE_URL + query;
    }

    private static Response sendGet(String query) {
        String url = buildUrl(query);
        return ApiConnection.get(url);
    }

    private static Response sendPost(String query, List<Parameter> parameters) {
        String url = buildUrl(query);
        return ApiConnection.post(url, parameters);
    }

    private static Response sendDelete(String query) {
        String url = buildUrl(query);
        return ApiConnection.delete(url);
    }
}
