package com.alive_n_clickin.commutity.application.api;

import com.alive_n_clickin.commutity.domain.IJourney;
import com.alive_n_clickin.commutity.domain.Journey;
import com.alive_n_clickin.commutity.infrastructure.api.ElectriCityApi;
import com.alive_n_clickin.commutity.infrastructure.api.IElectriCityApi;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;

/**
 * {@inheritDoc}<br><br>
 *
 * This class is not mean to be instantiated by outside classes (hence the package private access).
 *
 * This class uses IElectriCityApi to get response objects, and then transforms them to to domain
 * objects.
 *
 * @since 0.1
 */
class ElectricityAdapter implements IElectricityAdapter {
    private final static String CLASS_NUMBER = "9015";
    private final static String THM_NUMBER = "014";
    private final static String ELECTRICITY_LINE_NUMBER = "5055";
    private final static String ELECTRICITY_JOURNEY_ID_PREFIX = CLASS_NUMBER + THM_NUMBER + ELECTRICITY_LINE_NUMBER;

    /**
     * The current journey, with id and destination, for the bus with the given DGW.
     *
     * @param dgw id of the bus we are looking for.
     * @return journey object with journey id and destination if there was a valid response, null otherwise.
     */
    @Override
    public IJourney getCurrentJourney(String dgw) {
        IElectriCityApi electriCityApi = new ElectriCityApi();

        JsonJourney jsonJourney = electriCityApi.getLatestJourney(dgw);

        return new Journey(jsonJourney.getDestination(), ELECTRICITY_JOURNEY_ID_PREFIX + jsonJourney.getJourneyId());
    }
}
