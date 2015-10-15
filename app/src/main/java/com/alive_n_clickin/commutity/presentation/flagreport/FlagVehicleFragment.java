package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.Flag;

import java.util.ArrayList;

/**
 * The view with several different flags available for the user to flag vehicles. Launches a detailed
 * view when the user clicks on a flag.
 *
 * @since 0.1
 */
public class FlagVehicleFragment extends Fragment {
    private int mCurrentPosition = -1;

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTestButtons();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter = new FlagViewAdapter(getActivity(), flagButtons);

        GridView flagGrid = (GridView) rootView.findViewById(R.id.flagGridView);
        flagGrid.setAdapter(flagAdapter);
        flagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO Move all this to a background service that posts when it finds a bus.
                FlagButton button = flagAdapter.getItem(i);

                //Prepare arguments
                FlagVehicleDetailFragment detailFragment = new FlagVehicleDetailFragment();
                Bundle args = new Bundle();
                args.putInt(FlagVehicleDetailFragment.getARG_POSITION(), mCurrentPosition);

                //Add flag data
                args.putInt("flag_image_ID", button.getImageID());
                args.putString("flag_description", button.getDescription());
//                args.putString("bus_data", currentBus.getDGW());
                args.putInt("flag_type_ID", button.getType().getId());
                detailFragment.setArguments(args);

                //Switch view
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                        beginTransaction();
                transaction.replace(R.id.content_frame, detailFragment);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
        return rootView;
    }


    /**
     * Adds the different buttons. At the moment it's all hard coded for testing purposes
     */
    private void addTestButtons(){
        //TODO hard coded flag data for testing purposes
        if(flagButtons==null){
            flagButtons = new ArrayList<>();
        }
        flagButtons.add(new FlagButton(R.drawable.flag_full_300px, getString(R.string.flag_overcrowded), Flag.Type.OVERCROWDED));
        flagButtons.add(new FlagButton(R.drawable.flag_rowdy_300px, getString(R.string.flag_disturbance), Flag.Type.DISTURBANCES));
        flagButtons.add(new FlagButton(R.drawable.flag_delayed_300px, getString(R.string.flag_delayed), Flag.Type.DELAYED));
        flagButtons.add(new FlagButton(R.drawable.flag_dirty_300px, getString(R.string.flag_messy), Flag.Type.MESSY));
        flagButtons.add(new FlagButton(R.drawable.flag_pram_300px, getString(R.string.flag_pram), Flag.Type.NO_PRAMS));
        flagButtons.add(new FlagButton(R.drawable.flag_warm_300px, getString(R.string.flag_warm), Flag.Type.BAD_CLIMATE));
        flagButtons.add(new FlagButton(R.drawable.flag_other_300px, getString(R.string.flag_other), Flag.Type.OTHER));
    }
}
