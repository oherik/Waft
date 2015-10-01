package com.alive_n_clickin.commutity.presentation.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.Stop;

import java.util.List;

/**
 * Converts the stop to a string to display in a view
 */
public class SearchResultAdapter extends ArrayAdapter<Stop> {
    public SearchResultAdapter(Context context, List<Stop> result) {
        super(context, 0, result);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stop stop = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stop_search_result, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.stopResultNameView);
        nameView.setText(stop.getName());
        return convertView;
    }
}