package com.alive_n_clickin.commutity.presentation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;

/**
 * @author hjorthjort
 *         Created 07/10/15
 */
public class FlagImageView extends ImageView {

    private IFlag flag = null;

    public FlagImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFlag(IFlag flag) {
        this.flag = flag;
        Drawable d = getResources().getDrawable(R.drawable.flag_delayed_300px);
        setImageDrawable(d);
    }
}
