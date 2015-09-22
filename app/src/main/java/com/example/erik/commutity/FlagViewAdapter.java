package com.example.erik.commutity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter for transforming flag data into a grid view
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

    public void setFlags(ArrayList<FlagButton> flags) {
        this.flags = flags;
        notifyDataSetChanged();
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

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(currentContext);
            currentView = inflater.inflate(R.layout.flag_button_layout, parent, false);

            flagHolder = new Holder();
            flagHolder.flagText = (TextView) currentView.findViewById(R.id.flagText);
            flagHolder.flagImage = (ImageView) currentView.findViewById(R.id.flagImage);
            currentView.setTag(flagHolder);
        } else {
            //Flag holder already exist, retrieve it from the view's tag
            flagHolder = (Holder) convertView.getTag();
        }

        FlagButton button = flags.get(position);
        flagHolder.flagImage.setImageDrawable(button.getImage());
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
