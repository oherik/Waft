package com.alive_n_clickin.commutity.domain.flag;

/**
 * An enum for different flag types. Each flag type is associated with a string.
 */
public enum FlagType implements IFlagType {
    FOO ("Foo"),
    BAR ("Bar");

    private final String name;

    FlagType(String name) {
        this.name = name;
    }

    /**
     * @return the name string associated with this flag type. This name will never be null.
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("FlagType [name=%s]", this.name);
    }
}
