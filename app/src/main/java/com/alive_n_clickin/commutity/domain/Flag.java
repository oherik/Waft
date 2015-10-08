package com.alive_n_clickin.commutity.domain;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A concrete implementation of the IFlag interface. Objects of this class are immutable.
 */
@EqualsAndHashCode
public class Flag implements IFlag {
    /**
     * An enum for different flag types. Each flag type is associated with a string and a boolean that
     * determines whether or not a comment is required for that flag type.
     */
    public enum Type implements IFlagType {
        OTHER (1, true),
        OVERCROWDED (2),
        DELAYED (3),
        MESSY (4),
        BAD_CLIMATE (5),
        DISTURBANCES (6),
        NO_PRAMS (7);

        @Getter private final int id;
        private final boolean requiresComment;

        Type(int id) {
            this.id = id;
            this.requiresComment = false;
        }

        Type(int id, boolean requiresComment) {
            this.id = id;
            this.requiresComment = requiresComment;
        }

        public static Type getByID(int id) {
            switch (id) {
                case 1:
                    return OTHER;
                case 2:
                    return OVERCROWDED;
                case 3:
                    return DELAYED;
                case 4:
                    return MESSY;
                case 5:
                    return BAD_CLIMATE;
                case 6:
                    return DISTURBANCES;
                case 7:
                    return NO_PRAMS;
                default:
                    return null;
            }
        }

        @Override
        public boolean isCommentRequired() {
            return this.requiresComment;
        }

        @Override
        public String toString() {
            return String.format("FlagType [id=%s]", this.id);
        }

        @Override
        public int getId() {
            return this.id;
        }
    }

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

    @Override
    public String toString() {
        return String.format(
                "Flag [type=%s, comment=%s, createdTime=[%3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS]",
                this.type, this.comment, this.createdTime);
    }
}
