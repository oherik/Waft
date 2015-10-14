package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IVehicle;
import com.alive_n_clickin.commutity.util.event.CantSearchForVehiclesEvent;
import com.alive_n_clickin.commutity.util.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObserver;

import java.util.ArrayList;

/**
 * The view with several different flags available for the user to flag vehicles. Launches a detailed
 * view when the user clicks on a flag.
 *
 * @since 0.1
 */
public class FlagVehicleFragment extends Fragment implements IObserver {
    private int mCurrentPosition = -1;

    private IManager busManager;
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

        // Register observers
        MyApplication application = (MyApplication) this.getActivity().getApplicationContext();
        this.busManager = application.getManager();
        this.busManager.addObserver(this);

        final View rootView = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter = new FlagViewAdapter(getActivity(), flagButtons);
        final TextView textView = (TextView) rootView.findViewById(R.id.textViewBusInformation);

        // Set up on click listener for bus text
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canSearch = busManager.canSearch();
                if (canSearch) {
                    textView.setText(R.string.loading_looking_for_vehicle);
                } else {
                    textView.setText(R.string.enabling_search);
                }
                handleSearchRequest();
            }
        });

        this.updateBusText(rootView);

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

    private void handleSearchRequest() {
        this.busManager.searchForVehicles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.busManager.removeObserver(this);
    }

    private void updateBusText() {
        this.updateBusText(getView());
    }

    private void updateBusText(View view) {
        final TextView textView = (TextView) view.findViewById(R.id.textViewBusInformation);

        if (!this.busManager.canSearch()) {
            textView.setText(R.string.cant_search);
        } else if (this.busManager.isOnBus()) {
            IVehicle bus = this.busManager.getCurrentBus();
            String newText = getCurrentBusAsString(bus);
            textView.setText(newText);
        } else {
            textView.setText(R.string.no_buses_near);
        }
    }

    private String getCurrentBusAsString(IVehicle bus) {
        StringBuilder newText = new StringBuilder();
        newText.append(bus.getShortRouteName());
        newText.append(" ");
        newText.append(bus.getDestination());
        return newText.toString();
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

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof CurrentBusChangeEvent) {
            handleCurrentBusChangeEvent((CurrentBusChangeEvent) event);
        } else if (event instanceof CantSearchForVehiclesEvent) {
            handleCantSearchEvent((CantSearchForVehiclesEvent) event);
        }
    }

    private void handleCurrentBusChangeEvent(CurrentBusChangeEvent event) {
        this.updateBusText();
    }

    private void handleCantSearchEvent(CantSearchForVehiclesEvent event) {
        this.updateBusText();
    }
}
