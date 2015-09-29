package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alive_n_clickin.commutity.R;

/**
 * This class handles the first view presented to the user.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);
    }

    public void plusButtonOnClick(View view){
        Intent intent = new Intent(this,FlagVehicle.class);
        startActivity(intent);
    }
}
