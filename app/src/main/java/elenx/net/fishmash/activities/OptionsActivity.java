package elenx.net.fishmash.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuItem;

import elenx.net.fishmash.R;
import elenx.net.fishmash.updaters.WordListUpdater;

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
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();

            WordListUpdater wordListUpdater = new WordListUpdater(this);
            wordListUpdater.execute();

            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signal(String message)
    {
        if(progressDialog != null)
        {
            progressDialog.setTitle(message);
        }
    }
}
