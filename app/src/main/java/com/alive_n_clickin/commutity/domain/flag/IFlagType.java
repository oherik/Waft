package com.alive_n_clickin.commutity.domain.flag;

/**
 * An interface for flag types.
 */
public interface IFlagType {
    /**
     * @return the name associated with this flag type. The name should never be null.
     */
    String getName();

    /**
     * @return true if a comment is required for this flag type, otherwise false.
     */
    boolean isCommentRequired();
}
