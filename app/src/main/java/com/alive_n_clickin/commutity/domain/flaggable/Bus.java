package com.alive_n_clickin.commutity.domain.flaggable;

import com.alive_n_clickin.commutity.domain.flag.IFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the IBus interface.
 */
public class Bus implements IBus {
    private String DGW;
    private String VIN;
    private String licenseNumber;
    private String wifiBSSID;

    private List<IFlag> flags = new ArrayList<>();

    /**
     * Instantiates a new bus with the supplied DGW, VIN, licenseNumber, wifiBSSID and flags.
     *
     * @param DGW The Device Gateway for the bus. For example "Ericsson$100020".
     * @param VIN The Vehicle Identification Number for the bus. For example "YV3U0V222FA100020".
     * @param licenseNumber The number on the license plate of the bus. For example "EPO 131".
     * @param wifiBSSID The BSSID for the WiFi connection on board the bus. For example "00:13:95:13:49:f5".
     * @param flags The flags that this bus should be flagged with. Can be null.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public Bus(String DGW, String VIN, String licenseNumber, String wifiBSSID, List<IFlag> flags) {
        // TODO: Add stricter checking of the parameters to ensure that all the ID:s are valid?
        if (DGW == null
                || VIN == null
                || licenseNumber == null
                || wifiBSSID == null
                || flags == null) {
            throw new IllegalArgumentException();
        }

        this.DGW = DGW;
        this.VIN = VIN;
        this.licenseNumber = licenseNumber;
        this.wifiBSSID = wifiBSSID;
        this.flags = new ArrayList<>(flags);
    }

    /**
     * Instantiates a new bus with the supplied DGW, VIN, licenseNumber, wifiBSSID.
     *
     * @param DGW The Device Gateway for the bus. For example "Ericsson$100020".
     * @param VIN The Vehicle Identification Number for the bus. For example "YV3U0V222FA100020".
     * @param licenseNumber The number on the license plate of the bus. For example "EPO 131".
     * @param wifiBSSID The BSSID for the WiFi connection on board the bus. For example "00:13:95:13:49:f5".
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public Bus(String DGW, String VIN, String licenseNumber, String wifiBSSID) {
        this(DGW, VIN, licenseNumber, wifiBSSID, new ArrayList<IFlag>());
    }

    @Override
    public String getDGW() {
        return DGW;
    }

    @Override
    public String getVIN() {
        return VIN;
    }

    @Override
    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public String getWifiBSSID() {
        return wifiBSSID;
    }

    @Override
    public List<IFlag> getFlags() {
        return new ArrayList<>(this.flags);
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * Two buses are equal if their DGW, VIN, licenseNumber and wifiBSSID are equal, and if all
     * their flags are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        Bus other = (Bus) obj;

        return this.DGW.equals(other.DGW)
                && this.VIN.equals(other.VIN)
                && this.licenseNumber.equals(other.licenseNumber)
                && this.wifiBSSID.equals(other.wifiBSSID)
                && this.flags.equals(other.flags);
    }

    @Override
    public String toString() {
        return String.format("Bus [DGW=%s, VIN=%s, licenseNumber=%s, wifiBSSID=%s]",
                this.DGW, this.VIN, this.licenseNumber, this.wifiBSSID);
    }
}
