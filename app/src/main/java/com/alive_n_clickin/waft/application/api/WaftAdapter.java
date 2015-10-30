package com.alive_n_clickin.waft.application.api;

import com.alive_n_clickin.waft.domain.Flag;
import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.domain.IFlag;
import com.alive_n_clickin.waft.domain.IFlagType;
import com.alive_n_clickin.waft.infrastructure.api.ApiFactory;
import com.alive_n_clickin.waft.infrastructure.api.response.ConnectionException;
import com.alive_n_clickin.waft.infrastructure.api.IWaftApi;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonFlag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@inheritDoc}<br><br>
 *
 * This class is not meant to be instantiated by outside classes (hence the package private access).
 *
 * This class uses IWaftApi to get response objects, and then transforms them to to domain objects.
 *
 * @since 0.2
 */
class WaftAdapter implements IWaftAdapter {
    private final IWaftApi waftApi = ApiFactory.createWaftApi();

    @Override
    public List<IFlag> getFlagsForVehicle(String journeyId) throws ConnectionException {
        List<JsonFlag> jsonFlags = waftApi.getFlagsForJourney(journeyId);

        List<IFlag> flags = new ArrayList<>();

        for (JsonFlag jsonFlag : jsonFlags) {
            IFlagType flagType = Flag.Type.getByID(jsonFlag.getFlagType());
            flags.add(new Flag(flagType, jsonFlag.getComment(), new Date(), jsonFlag.get_id()));
        }

        return flags;
    }

    @Override
    public boolean flagBus(IElectriCityBus bus, IFlag flag) throws ConnectionException {
        return waftApi.addFlag(bus.getDGW(), bus.getJourneyId(), flag.getType().getId(), flag.getComment(),
                flag.getCreatedTime());
    }

    @Override
    public boolean deleteFlag(IFlag flag) throws ConnectionException {
        return waftApi.deleteFlag(flag.getId());
    }
}
