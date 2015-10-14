package com.alive_n_clickin.commutity.presentation.flagreport;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by OscarEvertsson on 14/10/15.
 */
public class Space extends RecyclerView.ItemDecoration {
    private int space;

    public Space(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double Space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        }
    }
}
