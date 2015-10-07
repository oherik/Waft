package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.presentation.FlagImageView;

import java.util.List;

/**
 * This class only purposes is to help with the insertion of each flag within the {@link ListAdapter}'s GridView.
 */
public class LittleFlagAdapter extends ArrayAdapter<IFlag> {

    public LittleFlagAdapter(Context context,List<IFlag> flagList){
        super(context,0,flagList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IFlag flag = getItem(position); //TODO: Use this flag object instead of hardcoding to get the image.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.little_flag, parent, false);
        }

        FlagImageView flagImageView = (FlagImageView) convertView.findViewById(R.id.littleFlagImageView);
        flagImageView.setFlag(flag);
//        Drawable d = getContext().getResources().getDrawable(R.drawable.flag_delayed_300px);
//        flagImageView.setImageDrawable(d);
        //TODO: Get the image belonging to this flag and set it (currently same image for each flag)

        return convertView;
    }

}
