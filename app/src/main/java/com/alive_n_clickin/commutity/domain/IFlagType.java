package com.alive_n_clickin.commutity.domain;

/**
 * An interface for flag types.
 */
public interface IFlagType {
    /**
     * @return the unique id used to identify this flag.
     */
    int getID();

    /**
     * @return true if a comment is required for this flag type, otherwise false.
     */
    boolean isCommentRequired();
}
