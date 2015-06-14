package net.elenx.fishmash.activities.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.elenx.fishmash.activities.core.drawer.DrawerActivity;

public abstract class ProgressDialogActivity extends DrawerActivity
{
    private ProgressDialog progressDialog;

    public void showProgressDialog()
    {
        dismissProgressDialog();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog()
    {
        if(progressDialog != null)
        {
            progressDialog.dismiss();
            progressDialog.cancel();
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

        return (netInfo == null || !netInfo.isConnected());
    }

    @Override
    protected void onPause()
    {
        dismissProgressDialog();
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        dismissProgressDialog();
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        dismissProgressDialog();
        super.onStop();
    }
}
