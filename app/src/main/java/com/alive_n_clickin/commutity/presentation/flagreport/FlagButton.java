package com.alive_n_clickin.commutity.presentation.flagreport;

import com.alive_n_clickin.commutity.domain.IFlagType;

/**
 * A class containing data for a flag button. The button consists visually of an image and a
 * description, and these are stored as a resource ID and a string. The type of flag is also
 * stored here.
 * @since 0.1
 */
public class FlagButton{
    private int imageID;
    private String description;
    private IFlagType type;

    /**
     * Creates a new button
     * @param imageID The resource ID of the image (e.g. R.drawable.full)
     * @param description   The flag description
     * @param type  The type of flag
     */
    public FlagButton(int imageID, String description, IFlagType type){
        this.imageID = imageID;
        this.description = description;
        this.type = type;
    }
    public String getDescription(){
        return description;
    }
    public IFlagType getType(){
        return type;
    }
    public int getImageID(){
        return imageID;
    }
}
