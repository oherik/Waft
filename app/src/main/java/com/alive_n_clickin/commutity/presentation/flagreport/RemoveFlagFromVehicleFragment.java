package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the view where you can remove flags on the current vehicle you're on.
 */
public class RemoveFlagFromVehicleFragment extends Fragment {
    private List<IFlag> flagList;

    private FlagsOnBusAdapter flagsOnBusAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (flagList == null) {
            flagList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.remove_flag_fragment, container, false);

        FloatingActionButton showPostFlagViewButton = (FloatingActionButton) rootView.
                findViewById(R.id.showPostFlagViewButton);
        showPostFlagViewButton.setOnClickListener(new ShowPostFlagViewButtonListener());

        this.flagsOnBusAdapter = new FlagsOnBusAdapter(getContext(), flagList);
        ListView flagListView = (ListView) rootView.findViewById(R.id.remove_flag_listView);
        flagListView.setAdapter(this.flagsOnBusAdapter);

        return rootView;
    }

    /**
     * This method handles updating the current adapter with a new list of flags.
     * @param flagList the new list to be displayed.
     */
    protected void updateFlags(List<IFlag> flagList) {
        if(this.flagList == null) {
            this.flagList = new ArrayList<>();
        }
        this.flagList.clear();
        this.flagList.addAll(flagList);

        if(flagsOnBusAdapter != null) {
            flagsOnBusAdapter.clear();
            flagsOnBusAdapter.addAll(flagList);
        }
    }


    /**
     * This class purposes is to handle onClick events from the showPostFlagViewButton
     */
    private class ShowPostFlagViewButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FlagVehicleFragment flagVehicleFragment = new FlagVehicleFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, flagVehicleFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }
    }
}
