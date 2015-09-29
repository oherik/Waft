package com.alive_n_clickin.commutity.domain.flag;

/**
 * An enum for different flag types. Each flag type is associated with a string and a boolean that
 * determines whether or not a comment is required for that flag type.
 */
public enum FlagType implements IFlagType {
    DELAY ("Försenad", false),
    CROWDED ("Full", false),
    MESSY ("Stökig", false),
    BAD_CLIMATE ("Dåligt klimat", false),
    VANDALIZED ("Vandalisering", false),
    OTHER ("Övrigt", true);

    private final String name;
    private final boolean requiresComment;

    FlagType(String name, boolean requiresComment) {
        this.name = name;
        this.requiresComment = requiresComment;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isCommentRequired() {
        return this.requiresComment;
    }

    @Override
    public String toString() {
        return String.format("FlagType [name=%s]", this.name);
    }
}
