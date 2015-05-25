package net.elenx.fishmash.updaters;

import android.os.AsyncTask;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class FishmashUpdater extends AsyncTask<Void, Integer, Void>
{
    private static final int TIMEOUT = 5 * 1000;
    static final RestTemplate restTemplate = new RestTemplate()
    {
        {
            ClientHttpRequestFactory clientHttpRequestFactory = getRequestFactory();

            if(clientHttpRequestFactory instanceof SimpleClientHttpRequestFactory)
            {
                SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = (SimpleClientHttpRequestFactory) clientHttpRequestFactory;

                simpleClientHttpRequestFactory.setConnectTimeout(TIMEOUT);
                simpleClientHttpRequestFactory.setReadTimeout(TIMEOUT);
            }
            else if (clientHttpRequestFactory instanceof HttpComponentsClientHttpRequestFactory)
            {
                HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = (HttpComponentsClientHttpRequestFactory) clientHttpRequestFactory;

                httpComponentsClientHttpRequestFactory.setReadTimeout(TIMEOUT);
                httpComponentsClientHttpRequestFactory.setConnectTimeout(TIMEOUT);
            }
        }

        @Override
        public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException
        {
            for(String key : urlVariables.keySet())
            {
                Log.e(key, urlVariables.get(key).toString());
            }

            return super.getForObject(url, responseType, urlVariables);
        }
    };

    private static final int CONNECTING = 0;
    private static final int DOWNLOADING = 1;
    private static final int CONVERTING = 2;
    private static final int SAVING = 3;

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
        }
        catch(Exception ignored)
        {
            // permit to continue - we will use cache, and take actions in onFailure()
        }

        return null;
    }

    protected void download()
    {
        // allow overriding, but do not force it
    }

    protected void convert()
    {
        // allow overriding, but do not force it
    }

    protected void save()
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
            Log.e("FishmashUpdater", "", e);

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
}
