package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IFlagType;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonFlag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a factory for creating ready-to-use flags. The idea is that you ask this factory
 * to build a flag from existing data (for example an ID or a response object received from
 * an external API), and that the factory simply responds with a Flag. The factory
 * is responsible for fetching all the data required for that vehicle from whatever sources it needs.
 * @since 0.2
 */
public class FlagFactory {

    /**
     * Takes a list of json response objects and returns a new list of domain objects.
     *
     * @param jsonFlags the list of json response objects to convert.
     * @return a new list with domain objects.
     */
    public static List<IFlag> getFlags(List<JsonFlag> jsonFlags){
        List<IFlag> flagList = new ArrayList<>();
        for(JsonFlag j: jsonFlags) {
            IFlagType flagType = Flag.Type.getByID(j.getFlagType());
            //TODO Change date to add proper date.
            IFlag flag = new Flag(flagType, j.getComment(), new Date());
            flagList.add(flag);
        }
        return flagList;
    }
}
