package com.alive_n_clickin.commutity.domain;

import android.graphics.Color;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A class modelling the ElectriCity bus. It contains an unique DGW ID. All ElectriCity buses
 * run on the 55 route, so the shortRouteName of an ElectriCityBus is always set automatically
 * to 55.
 *
 * @since 0.2
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ElectriCityBus extends AbstractVehicle implements IElectriCityBus {
    private static final String SHORT_ROUTE_NAME = "55"; // ElectriCity buses only run on the 55 route
    private static final String LINE_COLOR_IN_HEX = "#8FFF42";
    private static final int LINE_COLOR = Color.parseColor(LINE_COLOR_IN_HEX);

    @Getter private final String DGW;

    /**
     * Constructor. Initiates a new ElectriCity bus with shortRouteName 55.
     *
     * @param destination Where the vehicle is headed, e.g. "Sahlgrenska"
     * @param journeyID from Vasttrafik. This is the unique identification number for a certain
     *                  route. It gets changed any time the vehicle arrives to the end stop and
     *                  continues in the opposite direction.
     * @param DGW from ElectriCity
     * @throws NullPointerException if any parameter is null
     */
    public ElectriCityBus(@NonNull String destination, @NonNull String journeyID,
                          @NonNull String DGW, @NonNull List<IFlag> flags) {

        super(destination, SHORT_ROUTE_NAME, journeyID, flags, LINE_COLOR);
        this.DGW = DGW;
    }

    @Override
    public List<IFlag> getFlags() {
        return super.getFlags();
    }
}
