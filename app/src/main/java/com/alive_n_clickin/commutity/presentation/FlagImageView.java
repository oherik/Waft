package com.alive_n_clickin.commutity.presentation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IFlagType;

import java.util.HashMap;

/**
 * This is class is an ImageView specialized on flags from our system. It maps every flag type to an
 * image, and has a setFlag method that lets you specify what image it should show based on flag type.
 *
 * @since 0.2
 */
public class FlagImageView extends ImageView {

    /*
    By keeping the mapping from flag type to image file in this class and this class only, we avoid duplication
    and unnecessary strong coupling between the flag type and actual image
    */
    private HashMap<IFlagType, Integer> images;

    public FlagImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        images = new HashMap<>();
        images.put(Flag.Type.BAD_CLIMATE, R.drawable.flag_warm);
        images.put(Flag.Type.BROKEN_TICKET_STATION, R.drawable.flag_broken_ticket_station);
        images.put(Flag.Type.CALM, R.drawable.flag_calm);
        images.put(Flag.Type.DELAYED, R.drawable.flag_delayed);
        images.put(Flag.Type.DISTURBANCES, R.drawable.flag_rowdy);
        images.put(Flag.Type.EMPTY, R.drawable.flag_empty);
        images.put(Flag.Type.GOOD_DRIVER, R.drawable.flag_good_driver);
        images.put(Flag.Type.LOUD, R.drawable.flag_loud);
        images.put(Flag.Type.MESSY, R.drawable.flag_dirty);
        images.put(Flag.Type.NO_PRAM_SPOTS, R.drawable.flag_pram);
        images.put(Flag.Type.NO_WHEELCHAIR_SPOTS, R.drawable.flag_no_wheelchair_spots);
        images.put(Flag.Type.OTHER, R.drawable.flag_other);
        images.put(Flag.Type.OVERCROWDED, R.drawable.flag_full);
        images.put(Flag.Type.QUIET, R.drawable.flag_quiet);


    }

    /**
     * Sets the image of the FlagImageView. The image will be the one that represents the flag type
     * of the flag parameter
     * @param flag the flag whose type will be used to set the image
     */
    public void setFlag(IFlag flag) {
        int flagImageResourceNumber = images.get(flag.getType());
        Drawable d = getResources().getDrawable(flagImageResourceNumber);
        setImageDrawable(d);
    }
}
