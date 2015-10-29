package com.alive_n_clickin.waft.application.api;

import com.alive_n_clickin.waft.domain.IJourney;
import com.alive_n_clickin.waft.domain.Journey;
import com.alive_n_clickin.waft.infrastructure.api.ApiFactory;
import com.alive_n_clickin.waft.infrastructure.api.IElectriCityApi;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonJourney;

/**
 * {@inheritDoc}<br><br>
 *
 * This class is not meant to be instantiated by outside classes (hence the package private access).
 *
 * This class uses IElectriCityApi to get response objects, and then transforms them to to domain
 * objects.
 *
 * @since 0.1
 */
class ElectriCityAdapter implements IElectriCityAdapter {
    private final static String CLASS_NUMBER = "9015";
    private final static String THM_NUMBER = "014";
    private final static String ELECTRICITY_LINE_NUMBER = "5055";
    private final static String ELECTRICITY_JOURNEY_ID_PREFIX = CLASS_NUMBER + THM_NUMBER + ELECTRICITY_LINE_NUMBER;

    private final IElectriCityApi electriCityApi = ApiFactory.createElectriCityApi();

    /**
     * The current journey, with id and destination, for the bus with the given DGW.
     *
     * @param dgw id of the bus we are looking for.
     * @return journey object with journey id and destination if there was a valid response, null otherwise.
     */
    @Override
    public IJourney getCurrentJourney(String dgw) {
        JsonJourney jsonJourney = electriCityApi.getLatestJourney(dgw);
        return new Journey(jsonJourney.getDestination(), ELECTRICITY_JOURNEY_ID_PREFIX + padWithZeroes(jsonJourney.getJourneyId(), 5));
    }

    /**
     * Pads a string with zeroes. This is used when you want to fill a string with zeroes until it
     * meets a certain length requirement. For example if we have the string "15" and want it to be
     * of length 5, this method will return the string "00015".
     *
     * @param string the string to pad.
     * @param wantedLength the wanted length of the returned string
     * @return a new string consisting of the sent in string padded with zeroes so that it's size
     * equals wantedLength.
     */
    private static String padWithZeroes(String string, int wantedLength) {
        String zeroes = "";
        for (int i = 0; i < wantedLength; i++) {
            zeroes += "0";
        }

        String paddedString = zeroes + string;
        return paddedString.substring(paddedString.length() - wantedLength, paddedString.length());
    }
}
