package com.alive_n_clickin.commutity.presentation.flagreport;

/**
 * The different flag types available
 */
public enum FlagType {

    OTHER(1),FULL(2), LATE(3), DIRTY(4), ROWDY(5), WHEELCHAIR(6);

    public final int flagTypeID;

    FlagType(int flagTypeID) {
        this.flagTypeID = flagTypeID;
    }
}
