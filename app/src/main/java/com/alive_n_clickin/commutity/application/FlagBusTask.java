package com.alive_n_clickin.commutity.application;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IFlag;

/**
 * @author hjorthjort
 *         Created 15/10/15
 */
public class FlagBusTask extends AsyncTask<IFlag, Void, Boolean> {

    private final Context applicationContext;
    private final IManager busManager;

    public FlagBusTask(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.busManager = ((MyApplication) applicationContext).getManager();
    }

    @Override
    protected Boolean doInBackground(IFlag... params) {
        return busManager.addFlagToCurrentBus(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(applicationContext, R.string.flag_sent, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(applicationContext, R.string.flag_not_sent, Toast.LENGTH_LONG).show();
        }
    }
}
