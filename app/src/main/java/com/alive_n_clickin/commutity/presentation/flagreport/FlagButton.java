package com.alive_n_clickin.commutity.presentation.flagreport;


import android.graphics.drawable.Drawable;

/**
 * A class containing data for a flag button
 */
public class FlagButton{
    private int imageID;
    private String description;
    private FlagType type;

    /**
     * Creates a default button
     * @param imageID The resource ID of the image (e.g. R.drawable.full)
     * @param description   The flag description
     * @param type  The type of flag
     */
    public FlagButton(int imageID, String description, FlagType type){
        this.imageID = imageID;

        this.description = description;
        this.type = type;
    }
    public String getDescription(){
        return description;
    }
    public FlagType getType(){
        return type;
    }
    public int getImageID(){
        return imageID;
    }
}
