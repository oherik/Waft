package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;

/**
 * Created by mats on 20/10/15.
 */
public class MockElectriCityAdapter implements IElectricityAdapter {
    @Override
    public JsonJourney getJourneyInfo(String dgw) {
        return new JsonJourney("Johanneberg", "9015014505500118");
    }
}
