package net.elenx.fishmash.updaters;

import android.os.AsyncTask;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.JSON;

import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

abstract class FishmashUpdater extends AsyncTask<Void, Integer, Void>
{
    static final FishmashRest fishmashRest = new FishmashRest();

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

        if(finisher == null)
        {
            finisher = failure();
        }

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
                throw new Exception("we are offline");
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
            Log.e("FishmashUpdater", e.getCause().getMessage(), e);
        }

        return null;
    }

    void download() throws Exception
    {
        // allow overriding, but do not force it
    }

    void convert() throws JSONException
    {
        // allow overriding, but do not force it
    }

    void save() throws Exception
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

    Map<String, String> buildParameters() throws Exception
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() <= 0)
        {
            throw new Exception("user is not logged in");
        }

        Authenticate authenticate = authenticateDAO.selectAll().get(0);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(authenticate.getUser_id()));
        map.put("api_token", authenticate.getToken());

        return map;
    }

    HttpEntity<String> buildEntityWith(JSON json)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));

        return new HttpEntity<>(json.toJson(), requestHeaders);
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

    public void setUpdaterListener(UpdaterListener updaterListener)
    {
        this.updaterListener = updaterListener;
    }
}
