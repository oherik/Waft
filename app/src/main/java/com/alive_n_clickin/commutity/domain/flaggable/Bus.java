package com.alive_n_clickin.commutity.domain.flaggable;

/**
 * A concrete implementation of the IBus interface.
 */
public class Bus extends AbstractFlaggable implements IBus {
    private String DGW;
    private String VIN;
    private String licenseNumber;
    private String wifiBSSID;

    /**
     * Instantiates a new bus with the supplied DGW, VIN, licenseNumber and wifiBSSID.
     *
     * @param DGW The Device Gateway for the bus. For example "Ericsson$100020".
     * @param VIN The Vehicle Identification Number for the bus. For example "YV3U0V222FA100020".
     * @param licenseNumber The number on the license plate of the bus. For example "EPO 131".
     * @param wifiBSSID The BSSID for the WiFi connection on board the bus. For example "00:13:95:13:49:f5".
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public Bus(String DGW, String VIN, String licenseNumber, String wifiBSSID) {
        // TODO: Add stricter checking of the parameters to ensure that all the ID:s are valid?
        if (DGW == null
                || VIN == null
                || licenseNumber == null
                || wifiBSSID == null) {
            throw new IllegalArgumentException();
        }

        this.DGW = DGW;
        this.VIN = VIN;
        this.licenseNumber = licenseNumber;
        this.wifiBSSID = wifiBSSID;
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

    /**
     * {@inheritDoc}<br><br>
     *
     * Two buses are equal if their DGW, VIN, licenseNumber and wifiBSSID are equal.
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
                && this.wifiBSSID.equals(other.wifiBSSID);
    }

    @Override
    public String toString() {
        return String.format("Bus [DGW=%s, VIN=%s, licenseNumber=%s, wifiBSSID=%s]",
                this.DGW, this.VIN, this.licenseNumber, this.wifiBSSID);
    }
}
