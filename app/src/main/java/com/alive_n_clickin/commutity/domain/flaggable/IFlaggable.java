package com.alive_n_clickin.commutity.domain.flaggable;

import com.alive_n_clickin.commutity.domain.flag.IFlag;

import java.util.List;

/**
 * An interface for objects that should be flaggable.
 */
public interface IFlaggable {
    /**
     * Adds a flag to the object.
     *
     * @param flag The flag to add.
     * @throws IllegalArgumentException if flag is null.
     */
    void addFlag(IFlag flag);

    /**
     * Removes a flag from the object. If the flag hasn't been added to the object, nothing happens.
     *
     * @param flag The flag to remove.
     * @throws IllegalArgumentException if flag is null.
     */
    void removeFlag(IFlag flag);

    /**
     * @return a list of all added flags that haven't been removed. This method should never return
     * null. If no flags have been added, it returns an empty list.
     */
    List<IFlag> getFlags();
}
