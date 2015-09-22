package com.example.erik.commutity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FlagVehicleFragment extends Fragment {

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;

    public FlagVehicleFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagButtons = new ArrayList<FlagButton>();
        Resources resources = getResources();

        //TODO hard coded flag data for testing purposes
        flagButtons.add(new FlagButton((resources.getDrawable(R.drawable.full)), "Full", FlagType.FULL));
        flagButtons.add(new FlagButton((resources.getDrawable(R.drawable.full)), "Stökig", FlagType.ROWDY));
        flagButtons.add(new FlagButton((resources.getDrawable(R.drawable.full)), "Försenda", FlagType.LATE));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter = new FlagViewAdapter(getActivity(), flagButtons);

        GridView flagGrid = (GridView) rootView.findViewById(R.id.flagGridView);
        flagGrid.setAdapter(flagAdapter);
        flagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* The user clicked on an entry */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FlagButton button = flagAdapter.getItem(i);
                Toast.makeText(getActivity(), "" + button.getDescription(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
