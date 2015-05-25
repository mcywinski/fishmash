package net.elenx.fishmash.updaters;

import android.os.AsyncTask;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

abstract class FishmashUpdater extends AsyncTask<Void, Integer, Void>
{
    static final RestTemplate restTemplate = new RestTemplate();

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

    protected abstract void download();

    protected abstract void convert();

    protected abstract void save();

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

    final String getStringFrom(String apiSection)
    {
        try
        {
            URL url = new URL(Constant.API + apiSection);
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
            e.printStackTrace();

            return "";
        }
    }
}
