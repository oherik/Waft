package com.example.erik.commutity;

/**
 * A class containing data for a flag button
 */
public class FlagButton {
    private String description;
    private FlagType type;

    /**
     * Creates a default button
     * @param description   The flag description
     * @param type  The type of flag
     */
    public FlagButton(String description, FlagType type){
        this.description = description;
        this.type = type;
    }
}
