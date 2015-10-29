package com.alive_n_clickin.commutity.domain;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A concrete implementation of the IFlag interface. Objects of this class are immutable.
 *
 * @since 0.2
 */
@EqualsAndHashCode
@ToString
public class Flag implements IFlag {
    /**
     * An enum for different flag types. Each flag type is associated with a string and a boolean that
     * determines whether or not a comment is required for that flag type.
     */
    @ToString
    public enum Type implements IFlagType {
        OTHER (1, true),
        OVERCROWDED (2),
        DELAYED (3),
        MESSY (4),
        BAD_CLIMATE (5),
        DISTURBANCES (6),
        NO_PRAM_SPOTS(7),
        QUIET (8),
        LOUD (9),
        NO_WHEELCHAIR_SPOTS (10),
        BROKEN_TICKET_STATION(11),
        CALM(12),
        CLEAN(13),
        EMPTY(14),
        GOOD_DRIVER(15);




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
                    return NO_PRAM_SPOTS;
                case 8:
                    return QUIET;
                case 9:
                    return LOUD;
                case 10:
                    return NO_WHEELCHAIR_SPOTS;
                case 11:
                    return BROKEN_TICKET_STATION;
                case 12:
                    return CALM;
                case 13:
                    return CLEAN;
                case 14:
                    return EMPTY;
                case 15:
                    return GOOD_DRIVER;
                default:
                    return null;
            }
        }

        @Override
        public boolean isCommentRequired() {
            return this.requiresComment;
        }
    }

    private static final int COMMENT_REQUIRED_MINIMUM_LENGTH = 5;

    @Getter private final IFlagType type;
    @Getter private final String comment;
    @Getter private String id;
    private final Date createdTime;

    /**
     * Instantiates a new flag with the supplied type, comment and time of creation.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @param comment A comment for the flag.  If the supplied flag type requires a comment, the
     *                comment must be at least 5 characters long.
     * @param createdTime The time that the flag was created. If null,
     * @throws IllegalArgumentException if the supplied flag type requires a
     * comment and comment is not at least 5 characters long.
     * @throws NullPointerException if any parameter is null
     */
    public Flag(@NonNull IFlagType type, @NonNull String comment, @NonNull Date createdTime) {

        if (type.isCommentRequired() && (comment.trim().length() < COMMENT_REQUIRED_MINIMUM_LENGTH)) {
            throw new IllegalArgumentException(
                    String.format("A comment of at least %s characters is required for flag type %s",
                            COMMENT_REQUIRED_MINIMUM_LENGTH, type));
        }

        this.type = type;
        this.comment = comment;
        this.createdTime = new Date(createdTime.getTime());
    }

    public Flag(@NonNull IFlagType type, @NonNull String comment, @NonNull Date createdTime, @NonNull String id) {
        this.type = type;
        this.comment = comment;
        this.createdTime = createdTime;
        this.id = id;
    }

    /**
     * Instantiates a new flag with the supplied type and comment. Sets the time of creation of the
     * flag to the current time.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @param comment A comment for the flag. If null, comment will be set to an empty string. If
     *                the supplied flag type requires a comment, the comment must be at least 5
     *                characters longs
     * @throws NullPointerException if any parameter is nullg
     */
    public Flag(@NonNull IFlagType type, @NonNull String comment) {
        this(type, comment, new Date());
    }

    /**
     * Instantiates a new flag of the supplied type. Sets comment to an empty string and the time of
     * creation of the flag to the current time.
     *
     * @param type The flag type for the flag. See FlagType for more information.
     * @throws IllegalArgumentException if  the supplied flag type requires a comment.
     * @throws NullPointerException if the parameter is null
     */
    public Flag(@NonNull IFlagType type) {
        this(type, "", new Date());
    }

    /**
     * Creates a copy of the created time. It uses a copy constructor, since that is the preferred
     * method according to Joshua Bloch (Effective Java, 2001)
     *
     * @return When the flag was created
     */
    public Date getCreatedTime() {
        return new Date(createdTime.getTime());
    }
}
