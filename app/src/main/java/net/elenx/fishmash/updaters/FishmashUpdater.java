package net.elenx.fishmash.updaters;

import android.os.AsyncTask;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.OptionsActivity;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public abstract class FishmashUpdater extends AsyncTask<Void, Integer, Void> implements UpdaterInterface
{
    protected static final int CONNECTING = 0;
    protected static final int DOWNLOADING = 1;
    protected static final int CONVERTING = 2;
    protected static final int SAVING = 3;

    protected OptionsActivity optionsActivity;

    @Override
    protected final Void doInBackground(Void... params)
    {
        publishProgress(CONNECTING);

        if(!optionsActivity.isOnline())
        {
            return null;
        }

        publishProgress(DOWNLOADING);
        download();

        publishProgress(CONVERTING);
        convert();

        publishProgress(SAVING);
        save();

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... state)
    {
        super.onProgressUpdate(state);

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

    protected final String getStringFrom(String apiSection)
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
