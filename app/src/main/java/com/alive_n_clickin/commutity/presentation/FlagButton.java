package com.alive_n_clickin.commutity.presentation;


import android.graphics.drawable.Drawable;

/**
 * A class containing data for a flag button
 */
public class FlagButton{
    private Drawable image;
    private String description;
    private FlagType type;

    /**
     * Creates a default button
     * @param image The flag image
     * @param description   The flag description
     * @param type  The type of flag
     */
    public FlagButton(Drawable image, String description, FlagType type){
        this.image = image;
        this.description = description;
        this.type = type;
    }
    public String getDescription(){
        return description;
    }
    public Drawable getImage(){
        return image;
    }
    public FlagType getType(){
        return type;
    }
}
