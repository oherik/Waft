package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.erik.commutity.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FlagVehicleFragment extends Fragment {
    FlagClicked mCallback;
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    public interface FlagClicked{
        public void sendFlagDetailData(FlagButton buttonData, String busData); //TODO busData needs more specific info (line number etc)
    }

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;
    private String busData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagButtons = new ArrayList<FlagButton>();
        Resources resources = getResources();

        //TODO hard coded flag data for testing purposes
        flagButtons.add(new FlagButton(R.drawable.full, "Full", FlagType.FULL));
        flagButtons.add(new FlagButton(R.drawable.full, "Stökig", FlagType.ROWDY));
        flagButtons.add(new FlagButton(R.drawable.full, "Försenad", FlagType.LATE));
        busData = "55";

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
                Context currentContext = getActivity().getApplicationContext();
                FlagButton button = flagAdapter.getItem(i);

                FlagVehicleDetailFragment detailFragment = new FlagVehicleDetailFragment();
                Bundle args = new Bundle();
                args.putInt(FlagVehicleDetailFragment.ARG_POSITION, mCurrentPosition);
                detailFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.content_frame, detailFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });

        return rootView;
    }
}
