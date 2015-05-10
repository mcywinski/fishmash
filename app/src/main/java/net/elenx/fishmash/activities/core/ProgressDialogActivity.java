package net.elenx.fishmash.activities.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class ProgressDialogActivity extends Activity
{
    private ProgressDialog progressDialog;

    public void showProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog()
    {
        if(progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    public void signal(String message)
    {
        if(progressDialog != null)
        {
            progressDialog.setMessage(message);
        }
    }

    public boolean isOffline()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        return netInfo == null || !netInfo.isConnected();
    }

    @Override
    protected void onPause()
    {
        dismissProgressDialog();
        super.onPause();
    }
}
