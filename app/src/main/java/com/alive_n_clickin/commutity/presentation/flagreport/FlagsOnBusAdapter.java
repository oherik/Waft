package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.presentation.FlagImageView;

import java.util.List;

/**
 * Created by OscarEvertsson on 17/10/15.
 */
public class FlagsOnBusAdapter extends ArrayAdapter<IFlag>{

    public FlagsOnBusAdapter(Context currentContext, List<IFlag> flags) {
        super(currentContext, 0, flags); //The second parameter is the resource ID for a layout file containing a layout to use when instantiating views. Making it 0 means we are not sending any resource file to the super class.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //false specifies to not attach to root ViewGroup.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.remove_flag_card, parent, false);
        }
        IFlag currentFlag = getItem(position);

        FlagImageView flagTypeIcon = (FlagImageView) convertView.findViewById(R.id.flagIcon);
        flagTypeIcon.setFlag(currentFlag);


        TextView flagDescription = (TextView) convertView.findViewById(R.id.flagDescription);
        flagDescription.setText(currentFlag.getComment() + "");

        ImageButton removeButton = (ImageButton) convertView.findViewById(R.id.removeButton);
        removeButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_clear_black_48dp));
        //TODO: Add on click listener.


        return convertView;
    }






}
