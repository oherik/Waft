package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.List;

/**
 *
 */
public class LittleFlagAdapter extends ArrayAdapter<IFlag> {

    public LittleFlagAdapter(Context context,List<IFlag> flagList){
        super(context,0,flagList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IFlag flag = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.little_flag, parent, false);
        }

        ImageView flagImageView = (ImageView) convertView.findViewById(R.id.littleFlagImageView);;
        Drawable d = getContext().getResources().getDrawable(R.drawable.flag_delayed_300px);
        flagImageView.setImageDrawable(d);
        //TODO: Get the image and set it

        return convertView;
    }

}
