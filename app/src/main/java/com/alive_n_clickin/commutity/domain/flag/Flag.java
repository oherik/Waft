package com.alive_n_clickin.commutity.domain.flag;

import java.util.Date;

/**
 * A concrete implementation of the IFlag interface. Objects of this class are immutable.
 */
public class Flag implements IFlag {
    private static final int COMMENT_REQUIRED_MINIMUM_LENGTH = 5;

    private final IFlagType type;
    private final String comment;
    private final Date createdTime;

    /**
     * Instantiates a new flag with the supplied type, comment and time of creation.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @param comment A comment for the flag. If null, comment will be set to an empty string. If
     *                the supplied flag type requires a comment, the comment must be at least 5
     *                characters long.
     * @param createdTime The time that the flag was created. If null, createdTime will be set to
     *                    the current time.
     * @throws IllegalArgumentException if type is null, or if the supplied flag type requires a
     * comment and comment is not at least 5 characters long.
     */
    public Flag(IFlagType type, String comment, Date createdTime) {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        if (type.isCommentRequired() && (comment == null || comment.length() < COMMENT_REQUIRED_MINIMUM_LENGTH)) {
            throw new IllegalArgumentException(
                    String.format("A comment of at least %s characters is required for flag type %s",
                            COMMENT_REQUIRED_MINIMUM_LENGTH, type));
        }

        this.type = type;
        this.comment = (comment == null) ? "" : comment;
        this.createdTime = (createdTime == null) ? new Date() : new Date(createdTime.getTime());
    }

    /**
     * Instantiates a new flag with the supplied type and comment. Sets the time of creation of the
     * flag to the current time.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @param comment A comment for the flag. If null, comment will be set to an empty string. If
     *                the supplied flag type requires a comment, the comment must be at least 5
     *                characters longs
     * @throws IllegalArgumentException if type is null.
     */
    public Flag(IFlagType type, String comment) {
        this(type, comment, new Date());
    }

    /**
     * Instantiates a new flag of the supplied type. Sets comment to an empty string and the time of
     * creation of the flag to the current time.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @throws IllegalArgumentException if type is null, or the supplied flag type requires a comment.
     */
    public Flag(IFlagType type) {
        this(type, "", new Date());
    }

    @Override
    public IFlagType getType() {
        return type;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Date getCreatedTime() {
        return new Date(createdTime.getTime());
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * Two flags are equal if they are of the same type, have the same comment and were created
     * at the same time.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        Flag other = (Flag) obj;

        return this.type.equals(other.type)
                && this.comment.equals(other.comment)
                && this.createdTime.equals(other.createdTime);
    }

    @Override
    public String toString() {
        return String.format(
                "Flag [type=%s, comment=%s, createdTime=[%3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS]",
                this.type, this.comment, this.createdTime);
    }
}
