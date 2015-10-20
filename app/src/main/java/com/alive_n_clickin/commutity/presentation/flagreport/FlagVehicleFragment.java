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
    private ArrayList<FlagButton> flagButtons;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        //Sets the layout
        layoutManager = new GridLayoutManager(getContext(),
                getContext().getResources().getInteger(R.integer.flag_columns));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new CardDecorator(getContext()
                .getResources()
                .getInteger(R.integer.spacing_between_flag_buttons)));
        //Sets the adapter which handles creating cards.
        adapter = new RecyclerAdapter(flagButtons);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    /**
     * This class only purpose is to setting the margin between cards in the RecyclerView.
     */
    private class CardDecorator extends RecyclerView.ItemDecoration {
        private int space;

        public CardDecorator(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            int currentItem = parent.getChildLayoutPosition(view);
            outRect.bottom = space;
            outRect.right = space;

            // Remove the margin in between the two columns.
            if (currentItem % 2 == 0) {
                outRect.left = space;
            }
            // Add top margin only for the first item in the two columns to avoid double margin between items.
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
        flagButtons.add(new FlagButton(R.drawable.flag_empty, getString(R.string.flag_empty), Flag.Type.EMPTY));
        flagButtons.add(new FlagButton(R.drawable.flag_full, getString(R.string.flag_overcrowded), Flag.Type.OVERCROWDED));
        flagButtons.add(new FlagButton(R.drawable.flag_calm, getString(R.string.flag_calm), Flag.Type.CALM));
        flagButtons.add(new FlagButton(R.drawable.flag_rowdy, getString(R.string.flag_disturbance), Flag.Type.DISTURBANCES));
        flagButtons.add(new FlagButton(R.drawable.flag_pram, getString(R.string.flag_pram), Flag.Type.NO_PRAM_SPOTS));
        flagButtons.add(new FlagButton(R.drawable.flag_no_wheelchair_spots, getString(R.string.flag_no_wheelchair_spots), Flag.Type.NO_WHEELCHAIR_SPOTS));
        flagButtons.add(new FlagButton(R.drawable.flag_clean, getString(R.string.flag_clean), Flag.Type.CLEAN));
        flagButtons.add(new FlagButton(R.drawable.flag_dirty, getString(R.string.flag_messy), Flag.Type.MESSY));
        flagButtons.add(new FlagButton(R.drawable.flag_quiet, getString(R.string.flag_quite), Flag.Type.QUIET));
        flagButtons.add(new FlagButton(R.drawable.flag_loud, getString(R.string.flag_loud), Flag.Type.LOUD));
        flagButtons.add(new FlagButton(R.drawable.flag_good_driver, getString(R.string.flag_good_driver), Flag.Type.GOOD_DRIVER));
        flagButtons.add(new FlagButton(R.drawable.flag_warm, getString(R.string.flag_warm), Flag.Type.BAD_CLIMATE));
        flagButtons.add(new FlagButton(R.drawable.flag_broken_ticket_station, getString(R.string.flag_broken_ticket_station), Flag.Type.BROKEN_TICKET_STATION));
        flagButtons.add(new FlagButton(R.drawable.flag_other, getString(R.string.flag_other), Flag.Type.OTHER));
    }
}
