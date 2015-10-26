package com.alive_n_clickin.commutity.application.api;


import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;

/**
 * This interface represent the possible request to the Electricity API combined with their responses.
 *
 * @since 0.1
 */
public interface IElectricityAdapter {

    JsonJourney getJourneyInfo(String dgw);
}
