package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.util.FlagType;

import java.util.ArrayList;

/**
 * The view with several different flags available for the user to flag vehicles. Launches a detailed
 * view when the user clicks on a flag
 */
public class FlagVehicleFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;

    private final String LOG_TAG = FlagVehicleFragment.class.getSimpleName();

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;
    private String busData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagButtons = new ArrayList();

        //TODO hard coded flag data for testing purposes
        flagButtons.add(new FlagButton(R.drawable.full, "Full", FlagType.OVERCROWDED));
        flagButtons.add(new FlagButton(R.drawable.full, "Stökig", FlagType.MESSY));
        flagButtons.add(new FlagButton(R.drawable.full, "Försenad", FlagType.DELAYED));
        flagButtons.add(new FlagButton(R.drawable.full, "Övrigt", FlagType.OTHER));
        busData = "55";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView   = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter     = new FlagViewAdapter(getActivity(), flagButtons);

        GridView flagGrid   = (GridView) rootView.findViewById(R.id.flagGridView);
        flagGrid.setAdapter(flagAdapter);
        flagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* The user clicked on an entry */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context currentContext  = getActivity().getApplicationContext();
                FlagButton button       = flagAdapter.getItem(i);

                //Prepare arguments
                FlagVehicleDetailFragment detailFragment    = new FlagVehicleDetailFragment();
                Bundle args                                 = new Bundle();
                args.putInt(FlagVehicleDetailFragment.ARG_POSITION, mCurrentPosition);

                //Add flag data
                args.putInt("flag_image_ID", button.getImageID());
                args.putString("flag_description", button.getDescription());
                args.putString("bus_data", busData);        //TODO redo
                args.putInt("flag_type_ID", button.getType().flagTypeID);
                detailFragment.setArguments(args);

                //Switch view
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                        beginTransaction();
                transaction.replace(R.id.content_frame, detailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }

}
