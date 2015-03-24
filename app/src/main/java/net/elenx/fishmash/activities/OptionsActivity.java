package net.elenx.fishmash.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuItem;

import net.elenx.fishmash.R;
import net.elenx.fishmash.updaters.WordListUpdater;

public class OptionsActivity extends Activity
{
    private ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.update_wordlists)
        {
            WordListUpdater wordListUpdater = new WordListUpdater(this);
            wordListUpdater.execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }
}
