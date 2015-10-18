package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.presentation.FlagImageView;

import java.util.List;

/**
 * This class creates list items for the RemoveFlagFromVehicleFragment's ListView.
 */
public class FlagsOnBusAdapter extends ArrayAdapter<IFlag>{
    RemoveFlagFromVehicleFragment fragment;

    public FlagsOnBusAdapter(Context currentContext, List<IFlag> flags, RemoveFlagFromVehicleFragment fragment) {
        super(currentContext, 0, flags); //The second parameter is the resource ID for a layout file containing a layout to use when instantiating views. Making it 0 means we are not sending any resource file to the super class.
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //false specifies to not attach to root ViewGroup.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.remove_flag_card, parent, false);
        }
        final IFlag currentFlag = getItem(position);

        FlagImageView flagTypeIcon = (FlagImageView) convertView.findViewById(R.id.flagIcon);
        flagTypeIcon.setFlag(currentFlag);

        TextView flagDescription = (TextView) convertView.findViewById(R.id.flagDescription);
        flagDescription.setText(currentFlag.getComment() + "");

        ImageButton removeButton = (ImageButton) convertView.findViewById(R.id.removeButton);
        removeButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_clear_black_48dp));
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteFlag(currentFlag);
            }
        });

        /*
        Make the comment section scrollable. This needs to be done this way, since both
        the field and the whole list itself should be scrollable.
         */
        ScrollView commentView = (ScrollView) convertView.findViewById(R.id.commentScrollView);
        commentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        return convertView;
    }







}
