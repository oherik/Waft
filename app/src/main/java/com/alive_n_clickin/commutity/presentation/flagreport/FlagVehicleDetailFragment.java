package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.FlagBusTask;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IFlagType;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;

import lombok.AccessLevel;
import lombok.Getter;


/**
 * A class for showing the detailed view when flagging a vehicle. The view contains a flag, its name,
 * a comment field and a button for sending the flag. This fragment class handles then visual elements
 * of this.
 *
 * @since 0.1
 */

public class FlagVehicleDetailFragment extends Fragment {
    @Getter(AccessLevel.PROTECTED) private final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;
    private IFlagType flagType;
    private TextView charsLeft;
    private Context currentContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Layout inflation
        View view =  inflater.inflate(R.layout.fragment_flag_vehicle_detail,
                container, false);
        Button sendButton = (Button) view.findViewById(R.id.flagDetailSendButton);
        Button cancelButton = (Button) view.findViewById(R.id.flagDetailCancelButton);

        // Check if wifi is enabled, if not display a message, if it is, try sending the flag
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWifiEnabled = new WifiHelper(getActivity()).isWifiEnabled();
                if (!isWifiEnabled) {
                    showEnableWifiAlert();
                } else {
                    trySendingFlag();
                }
            }
        });

        /*
        Set a listener on the cancel button, if pressed it should simulate a back button press
        (i.e. returning the user to the previous view)
        */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFlagFragment();
            }
        });

        charsLeft = (TextView) view.findViewById(R.id.commentCharsLeft);
        final EditText commentField = (EditText) view.findViewById(R.id.flagDetailCommentField);
        commentField.addTextChangedListener(charsLeftTextWatcher);

        //Make sure the content is pushed up when the keyboard is showed
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //Set focus on the comment field
        commentField.requestFocus();

        //Show the keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        this.currentContext = container.getContext();
        return view;
    }

    /**
     * Listens to an edit text field. Shows to the users how many character he or she has left,
     * if it is 15 or less than the maximum value (otherwise it sets the text view invisible)
     */
    private final TextWatcher charsLeftTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int maxLength = currentContext.getResources().getInteger(R.integer.flag_comment_max_length);
            int numberOfCharsLeft = maxLength - s.length();
            if(numberOfCharsLeft<16) {
                charsLeft.setVisibility(TextView.VISIBLE);
                charsLeft.setText(String.valueOf(numberOfCharsLeft));
            } else {
                charsLeft.setVisibility(TextView.INVISIBLE);
            }
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }

        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * Tries sending the flag data by calling on the http request class. If it's successful it
     * switches back to the previous view, otherwise it displays a message why it couldn't be sent.
     * At the moment the only possible error is that the comment is too short when submitting an
     * "other" flag, so a toast about this is displayed.
     */
    private void trySendingFlag() {
        View rootView = getActivity().findViewById(android.R.id.content);
        TextView commentView = (TextView) rootView.findViewById(R.id.flagDetailCommentField);
        String comment = commentView.getText().toString();

        IFlag flag;
        try {
            flag = new Flag(flagType, comment);
            FlagBusTask flagBusTask = new FlagBusTask(currentContext.getApplicationContext());
            flagBusTask.execute(flag);
            switchToFlagFragment();
        } catch (IllegalArgumentException e) {
            // flag couldn't be created
            //TODO make the comment field blink or make some other non-intrusive indicator.
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.flag_longer_comment_needed), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Alerts the user that  wifi need to be enabled.
     */
    private void showEnableWifiAlert() {

        AlertDialog alertDialog = new AlertDialog.Builder(currentContext)
                .setTitle(R.string.enable_wifi_alert_title)
                .setMessage(R.string.enable_wifi_alert_message)
                .setPositiveButton(R.string.enable_wifi_alert_yesbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new WifiHelper(currentContext).enableWifi();
                    }

                }).setNegativeButton(R.string.enable_wifi_alert_nobutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .show();
    }
    /**
     * Switches the view back to the main flag fragment
     */
    private void switchToFlagFragment(){
        //Hide keyboard from the current window
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText commentField  =  (EditText) getActivity().findViewById(R.id.flagDetailCommentField);
        imm.hideSoftInputFromWindow(commentField.getWindowToken(), 0);

        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Get arguments sent by the starting class
        Bundle args = getArguments();

        //Set text
        View rootView           = getActivity().findViewById(android.R.id.content);
        TextView description    = (TextView) rootView.findViewById(R.id.flagDetailDescription);
        description.setText(args.getString("flag_description"));

        //Set images
        ImageView flagImageView = (ImageView) rootView.findViewById(R.id.flagDetailImage);
        int flagImageID         = args.getInt("flag_image_ID");
        Drawable flagImage      = getActivity().getResources().getDrawable(flagImageID);
        flagImageView.setImageDrawable(flagImage);


        //Set additional data
        flagType = Flag.Type.getByID(args.getInt("flag_type_ID"));
        //TODO add more data

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

}
