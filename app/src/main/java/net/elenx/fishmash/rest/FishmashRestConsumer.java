package net.elenx.fishmash.rest;

import android.os.AsyncTask;

import net.elenx.fishmash.activities.OptionsActivity;

public class FishmashRestConsumer<ResultClass> extends AsyncTask<Void, Void, Void>
{
    final private OptionsActivity optionsActivity;
    final private RestListener<ResultClass> restListener;
    private ResultClass result;

    public FishmashRestConsumer(OptionsActivity optionsActivity, RestListener<ResultClass> restListener)
    {
        this.optionsActivity = optionsActivity;
        this.restListener = restListener;
    }

    @Override
    protected void onPreExecute()
    {
        optionsActivity.showProgressDialog();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        if(result == null)
        {
            restListener.onFailure();
        }
        else
        {
            restListener.onSuccess(result);
        }

        optionsActivity.dismissProgressDialog();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        try
        {
            result = restListener.makeRequest();
        }
        catch(Exception ignored)
        {

        }

        return null;
    }
}
