package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.NearbyVehiclesScanner;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;
import com.alive_n_clickin.commutity.util.FlagType;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * The view with several different flags available for the user to flag vehicles. Launches a detailed
 * view when the user clicks on a flag
 */
public class FlagVehicleFragment extends Fragment implements WifiChangeListener {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;

    private final String LOG_TAG = FlagVehicleFragment.class.getSimpleName();

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;
    private String busData;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagButtons = new ArrayList();

        //TODO hard coded flag data for testing purposes
        flagButtons.add(new FlagButton(R.drawable.flag_full_300px, getString(R.string.flag_overcrowded), FlagType.OVERCROWDED));
        flagButtons.add(new FlagButton(R.drawable.flag_rowdy_300px, getString(R.string.flag_disturbance), FlagType.DISTURBANCES));
        flagButtons.add(new FlagButton(R.drawable.flag_delayed_300px, getString(R.string.flag_delayed), FlagType.DELAYED));
        flagButtons.add(new FlagButton(R.drawable.flag_dirty_alt2_300px, getString(R.string.flag_messy), FlagType.MESSY));
        flagButtons.add(new FlagButton(R.drawable.flag_pram_300px, getString(R.string.flag_pram), FlagType.NO_PRAMS));
        flagButtons.add(new FlagButton(R.drawable.flag_other_black_300px, getString(R.string.flag_other), FlagType.OTHER));
        busData = "55";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WifiBroadcastReceiver.register(this);

        final View rootView   = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter     = new FlagViewAdapter(getActivity(), flagButtons);

        GridView flagGrid   = (GridView) rootView.findViewById(R.id.flagGridView);
        flagGrid.setAdapter(flagAdapter);
        flagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* The user clicked on an entry */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO Move all this to a background service that posts when it finds a bus.
                Context currentContext = getActivity().getApplicationContext();
                FlagButton button = flagAdapter.getItem(i);

                //Prepare arguments
                FlagVehicleDetailFragment detailFragment = new FlagVehicleDetailFragment();
                Bundle args = new Bundle();
                args.putInt(FlagVehicleDetailFragment.ARG_POSITION, mCurrentPosition);

                //Add flag data
                args.putInt("flag_image_ID", button.getImageID());
                args.putString("flag_description", button.getDescription());
                busData = NearbyVehiclesScanner.getInstance().getBestGuess(currentContext);
                args.putString("bus_data", busData);
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

        writeOutBestGuess(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WifiBroadcastReceiver.unregister(this);
    }

    private void writeOutBestGuess(@NonNull View rootView) {
        showWifiPromptIfNeeded();
        String bestGuess = NearbyVehiclesScanner.getInstance().getBestGuess(getContext());
        TextView textView = (TextView) rootView.findViewById(R.id.textViewBusInformation);

        if (!WifiHelper.getInstance().isWifiEnabled(getContext())) {
            textView.setText(R.string.you_must_activate_wifi);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiHelper.getInstance().enableWifi(getContext());
                }
            });
        }
        else if (bestGuess != null) {
            textView.setText(bestGuess);
        } else {
            textView.setText(R.string.no_buses_near);
        }
    }

    private void showWifiPromptIfNeeded() {
    }

    @Override
    public void onWifiChanged() {
        writeOutBestGuess(getView());
    }
}
