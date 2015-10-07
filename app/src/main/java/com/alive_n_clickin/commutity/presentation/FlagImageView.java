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
 * @author hjorthjort
 *         Created 07/10/15
 */
public class FlagImageView extends ImageView {

    private IFlag flag = null;
    private HashMap<IFlagType, Integer> images;

    public FlagImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        images = new HashMap<>();
        images.put(Flag.Type.DELAYED, R.drawable.flag_delayed_300px);
        images.put(Flag.Type.DISTURBANCES, R.drawable.flag_rowdy_300px);
        images.put(Flag.Type.MESSY, R.drawable.flag_dirty_alt1_300px);
        images.put(Flag.Type.OVERCROWDED, R.drawable.flag_full_300px);
        images.put(Flag.Type.NO_PRAMS, R.drawable.flag_pram_300px);
        images.put(Flag.Type.OTHER, R.drawable.flag_other_black_300px);
    }

    public void setFlag(IFlag flag) {
        this.flag = flag;
        int flagImageResourceNumber = images.get(flag.getType());
        Drawable d = getResources().getDrawable(flagImageResourceNumber);
        setImageDrawable(d);
    }
}
