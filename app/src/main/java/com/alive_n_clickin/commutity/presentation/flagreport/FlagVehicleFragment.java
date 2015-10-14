package com.alive_n_clickin.commutity.presentation.flagreport;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private int SPACING_BETWEEN_FLAGBUTTONS = 20;

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;

    //RecyclerAdapter
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int AMOUNT_OF_COLUMNS = 2;

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

        final View rootView = inflater.inflate(R.layout.recycler, container, false);

        //ReyclerStuff start
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(), AMOUNT_OF_COLUMNS);

        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new Space(SPACING_BETWEEN_FLAGBUTTONS));
        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(flagButtons);
        mRecyclerView.setAdapter(mAdapter);
        //End

        /* TODO: Set up on click listeners to change fragment
        //Switch view
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                beginTransaction();
        transaction.replace(R.id.content_frame, detailFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        */

        return rootView;
    }

    /**
     * This class only purpose is to handles setting margin for the RecyclerView.
     */
    private class Space extends RecyclerView.ItemDecoration {
        private int space;

        public Space(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            int currentItem = parent.getChildLayoutPosition(view);
            outRect.bottom = space;
            outRect.right = space;

            // Remove the margin between the two columns.
            if (currentItem%2 == 0) {
                outRect.left = space;
            }
            // Add top margin only for the first item to avoid double Space between items.
            if (currentItem == 0 || currentItem == 1) {
                outRect.top = space;
            }
        }
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
