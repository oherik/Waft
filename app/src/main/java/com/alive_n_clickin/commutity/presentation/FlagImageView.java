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

import lombok.Getter;

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
        images.put(Flag.Type.DELAYED, R.drawable.flag_delayed_300px);
        images.put(Flag.Type.DISTURBANCES, R.drawable.flag_rowdy_300px);
        images.put(Flag.Type.MESSY, R.drawable.flag_dirty_300px);
        images.put(Flag.Type.OVERCROWDED, R.drawable.flag_full_300px);
        images.put(Flag.Type.NO_PRAMS, R.drawable.flag_pram_300px);
        images.put(Flag.Type.OTHER, R.drawable.flag_other_300px);
        images.put(Flag.Type.BAD_CLIMATE, R.drawable.flag_warm_300px);
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
