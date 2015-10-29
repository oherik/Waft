package com.alive_n_clickin.waft.application;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.alive_n_clickin.waft.R;
import com.alive_n_clickin.waft.domain.IFlag;

/**
 * An async task that adds a flag to the current bus. Since the flagging process involves network
 * requests this task must be done asynchronously. When the task is completed, a toast is displayed
 * in the application context with either a success message or a failure message.
 */
public class FlagCurrentBusTask extends AsyncTask<IFlag, Void, Boolean> {
    private final Context applicationContext;
    private final IManager manager;

    public FlagCurrentBusTask(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.manager = ((CentralApplication) applicationContext).getManager();
    }

    @Override
    protected Boolean doInBackground(IFlag... params) {
        return manager.addFlagToCurrentBus(params[0]);
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
