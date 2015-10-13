package com.alive_n_clickin.commutity.infrastructure.api;


/**
 * This interface represent the possible request to the Electricity API combined with their responses.
 *
 * @since 0.1
 */
public interface IElectricityAdapter {

    Journey getJourneyInfo(String dgw);
}
