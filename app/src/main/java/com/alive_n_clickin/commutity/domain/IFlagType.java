package com.alive_n_clickin.commutity.domain;

/**
 * An interface for flag types. A flag type should be able to provide an unique id that can be used
 * to identify it, and if a comment is required for that specific flag type.
 */
public interface IFlagType {
    /**
     * @return the unique id used to identify this flag.
     */
    int getId();

    /**
     * @return true if a comment is required for this flag type, otherwise false.
     */
    boolean isCommentRequired();
}
