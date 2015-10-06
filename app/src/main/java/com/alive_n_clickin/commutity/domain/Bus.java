package com.alive_n_clickin.commutity.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A concrete implementation of the IBus interface.
 */
@EqualsAndHashCode
public class Bus implements IBus {
    @Getter private String DGW;
    @Getter private String destination;
    @Getter private String journeyName; // Västtrafiks "turnummer"
    @Getter private String routeNumber;

    private List<IFlag> flags = new ArrayList<>();

    /**
     * Instantiates a new bus with the supplied DGW, VIN, licenseNumber, wifiBSSID and flags.
     *
     * @param DGW The Device Gateway for the bus. For example "Ericsson$100020".
     * @param destination The destination for the bus. For example "Lindholmen".
     * @param journeyName The journey name for the journey that the bus is on currently. This is
     *                    Västtrafiks "turnummer".
     * @param routeNumber The route number for the bus. For example "55".
     * @param flags The flags that this bus should be flagged with.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public Bus(String DGW, String destination, String journeyName, String routeNumber, List<IFlag> flags) {
        // TODO: Add stricter checking of the parameters to ensure that all the ID:s are valid?
        if (DGW == null
                || destination == null
                || journeyName == null
                || routeNumber == null
                || flags == null) {
            throw new IllegalArgumentException();
        }

        this.DGW = DGW;
        this.destination = destination;
        this.journeyName = journeyName;
        this.routeNumber = routeNumber;
        this.flags = new ArrayList<>(flags);
    }

    @Override
    public List<IFlag> getFlags() {
        return new ArrayList<>(this.flags);
    }

    @Override
    public String toString() {
        return String.format("Bus [DGW=%s, destination=%s, journeyName=%s, routeNumber=%s]",
                this.DGW, this.destination, this.journeyName, this.routeNumber);
    }
}
