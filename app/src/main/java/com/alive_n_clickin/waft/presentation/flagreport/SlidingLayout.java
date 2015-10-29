package com.alive_n_clickin.waft.presentation.flagreport;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Used for making it possible for x- and y-fractions to be used when animating the
 * detailed flag view fragment. This will make it possible, in the future, to have the
 * fragment animate for a set percentage of the screen, something that wouldn't have
 * been possible if just using the built in animations.
 */
public class SlidingLayout extends RelativeLayout {

    public SlidingLayout(Context context) {
        super(context);
    }

    public SlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the translation of the layout, depending on a given fraction. It does this
     * by measuring the height of the view, making it device-independent.
     * @param fraction  The fraction of the screen the layout should extend to. Must be
     *                  between 0 and 1.0
     * @throws IndexOutOfBoundsException if the input is not between 0 and 1.0
     */
    public void setYFraction(float fraction) {
        if (fraction < 0 || fraction > 1.0){
            throw new IndexOutOfBoundsException("The fraction must be [0, 1.0]");
        }

        // Fixes a bug where the view would show for a frame, unanimated, on top of everything
        // else. This was because the height had not yet been measured. This if/else renders
        // the view invisible if no height has been set.
        if (getHeight() == 0) {
            setVisibility(View.INVISIBLE);
        } else if (getVisibility() == View.INVISIBLE) {
            setVisibility(View.VISIBLE);
        }
        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    /**
     * Measures the y translation in regards to the view height, and returns the fraction.
     * @return  The fraction of the view height currently translated
     */
    public float getYFraction() {
        // If the height hasn't yet been measured, don't bother with trying to divide by 0
        float height = getHeight();
        if (height == 0) {
            return 0;
        }
        return getTranslationY() / height;
    }
}
