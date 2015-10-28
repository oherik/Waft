package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IStop;

import java.util.List;

/**
 * Converts the stop to a string to display in a view, i.e. the search result list.
 *
 * @since 0.1
 */
public class SearchResultAdapter extends ArrayAdapter<IStop> {
    public SearchResultAdapter(Context context, List<IStop> result) {
        super(context, 0, result);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IStop stop = getItem(position);
        if (convertView == null) {
            //false specifies to not attach to root ViewGroup.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stop_search_result, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.stopResultNameView);
        nameView.setText(stop.getName());
        return convertView;
    }
}