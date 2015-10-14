package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter for transforming flag data into a grid view.
 *
 * @since 0.1
 */
public class FlagViewAdapter extends BaseAdapter  {
    private Context currentContext;
    private List<FlagButton> flags;

    /**
     * Constructor for the adapter
     *
     * @param currentContext The current context
     * @param flags          Data containing the flag buttons
     */
    public FlagViewAdapter(Context currentContext, ArrayList<FlagButton> flags) {
        this.currentContext = currentContext;
        this.flags = flags;
    }

    @Override
    public int getCount() {
        return flags.size();
    }

    @Override
    public FlagButton getItem(int position) {
        return flags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder flagHolder;
        View currentView = convertView;

        //Set a new flag holder
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(currentContext);
            currentView             = inflater.inflate(R.layout.flag_button_layout, parent, false);

            flagHolder              = new Holder();
            flagHolder.flagText     = (TextView) currentView.findViewById(R.id.flagText);
            flagHolder.flagImage    = (ImageView) currentView.findViewById(R.id.flagImage);
            currentView.setTag(flagHolder);
        } else {
            //Flag holder already exist, retrieve it from the view's tag
            flagHolder = (Holder) convertView.getTag();
        }

        //Set the image and text in the holder based ono what's in the button data object
        FlagButton button   = flags.get(position);
        Drawable flagImage  = currentContext.getResources().getDrawable(button.getImageID());
        flagHolder.flagImage.setImageDrawable(flagImage);
        flagHolder.flagText.setText(button.getDescription());
        return currentView;
    }

    /**
     * An class for holding both a image and a text for a flag
     */
    private static class Holder{
        ImageView flagImage;
        TextView flagText;
    }
}
