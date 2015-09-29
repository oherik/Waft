package com.alive_n_clickin.commutity.domain.flag;

import java.util.Date;

/**
 * An interface for modelling flags.
 */
public interface IFlag {
    /**
     * @return the type of this flag. This method should never return null.
     */
    IFlagType getType();

    /**
     * @return the comment of this flag. This method should never return a null string.
     */
    String getComment();

    /**
     * @return the time of creation of the flag. This method should never return a null date.
     */
    Date getCreatedTime();
}
