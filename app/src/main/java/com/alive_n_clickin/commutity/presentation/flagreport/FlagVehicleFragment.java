package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.NearbyVehiclesScanner;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;

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
        flagButtons.add(new FlagButton(R.drawable.full, "Full", FlagType.FULL));
        flagButtons.add(new FlagButton(R.drawable.full, "Stökig", FlagType.ROWDY));
        flagButtons.add(new FlagButton(R.drawable.full, "Försenad", FlagType.LATE));
        flagButtons.add(new FlagButton(R.drawable.full, "Övrigt", FlagType.OTHER));
        busData = "55";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        //TODO handle the location button click properly
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.positionButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Debug to find out why the isWifiEnabled never gives true.
                //Works on my physical device –Rikard
                if(WifiHelper.getInstance().isWifiEnabled(getContext())){
                    writeOutBestGuess(rootView);
                } else {
                    showEnableWifiAlert();
                }
            }
        });

        return rootView;
    }

    private void writeOutBestGuess(View rootView) {
        String bestGuess = NearbyVehiclesScanner.getInstance().getBestGuess(getContext());
        TextView textView = (TextView) rootView.findViewById(R.id.textViewBusInformation);
        Log.d(LOG_TAG, "Wifi enabled");

        if(bestGuess != null){
            textView.setText(bestGuess);
        } else {
            textView.setText("No buses near :(");
        }
    }

    private void showEnableWifiAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Please turn on your wifi")
                .setMessage("To access your current location we need access to your wifi please turn it on :)")
                .setPositiveButton("Change Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WifiHelper.getInstance().enableWifi(getContext());
                    }

                }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .show();
    }

}
