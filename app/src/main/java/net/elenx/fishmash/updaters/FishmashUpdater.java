package net.elenx.fishmash.updaters;

import android.os.AsyncTask;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class FishmashUpdater extends AsyncTask<Void, Integer, Void>
{
    static final FishmashRest fishmashRest = new FishmashRest(5);

    private static final int CONNECTING = 0;
    private static final int DOWNLOADING = 1;
    private static final int CONVERTING = 2;
    private static final int SAVING = 3;

    private UpdaterListener updaterListener;
    private Runnable finisher;

    final OptionsActivity optionsActivity;

    FishmashUpdater(OptionsActivity optionsActivity)
    {
        this.optionsActivity = optionsActivity;
    }

    @Override
    protected void onPreExecute()
    {
        optionsActivity.showProgressDialog();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        optionsActivity.dismissProgressDialog();
        finisher.run();
    }

    @Override
    protected final Void doInBackground(Void... params)
    {
        try
        {
            publishProgress(CONNECTING);

            if(optionsActivity.isOffline())
            {
                return null;
            }

            publishProgress(DOWNLOADING);
            download();

            publishProgress(CONVERTING);
            convert();

            publishProgress(SAVING);
            save();

            finisher = success();
        }
        catch(Exception e)
        {
            Log.e("FishmashUpdater", e.getMessage(), e);
            // permit to continue - we will use cache, and take actions in onFailure()

            finisher = failure();
        }

        return null;
    }

    protected void download() throws Exception
    {
        // allow overriding, but do not force it
    }

    protected void convert() throws JSONException
    {
        // allow overriding, but do not force it
    }

    protected void save() throws Exception
    {
        // allow overriding, but do not force it
    }

    @Override
    protected void onProgressUpdate(Integer... state)
    {
        switch(state[0])
        {
            case CONNECTING:
                optionsActivity.signal(optionsActivity.getString(R.string.connecting));
                break;

            case DOWNLOADING:
                optionsActivity.signal(optionsActivity.getString(R.string.downloading));
                break;

            case CONVERTING:
                optionsActivity.signal(optionsActivity.getString(R.string.converting));
                break;

            case SAVING:
                optionsActivity.signal(optionsActivity.getString(R.string.saving));
                break;
        }
    }

    final String getStringFrom(String address)
    {
        try
        {
            URL url = new URL(address);
            Scanner scanner = new Scanner(url.openStream());

            StringBuilder stringBuilder = new StringBuilder();
            scanner.useDelimiter("\\Z");

            while(scanner.hasNext())
            {
                stringBuilder.append(scanner.nextLine());
            }

            return stringBuilder.toString();
        }
        catch(IOException e)
        {
            Log.e("FishmashUpdater", e.getMessage(), e);

            return "";
        }
    }

    protected Map<String, String> buildParameters()
    {
        Map<String, String> parameters = new HashMap<>();
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() > 0)
        {
            parameters.put("api_token", authenticateDAO.selectAll().get(0).getToken());
        }

        return parameters;
    }

    public void setUpdaterListener(UpdaterListener updaterListener)
    {
        this.updaterListener = updaterListener;
    }

    private Runnable success()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                if(updaterListener != null)
                {
                    updaterListener.onSuccess();
                }
            }
        };
    }

    private Runnable failure()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                if(updaterListener != null)
                {
                    updaterListener.onFailure();
                }
            }
        };
    }
}
