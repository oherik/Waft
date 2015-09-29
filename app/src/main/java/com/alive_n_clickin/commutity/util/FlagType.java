package com.alive_n_clickin.commutity.util;

/**
 * The different flag types available
 */
public enum FlagType {

    OTHER(1), OVERCROWDED(2), DELAYED(3), MESSY(4), BAD_CLIMATE(5), DISTURBANCES(6), NO_PRAMS(7);

    public final int flagTypeID;

    FlagType(int flagTypeID) {
        this.flagTypeID = flagTypeID;
    }
}
