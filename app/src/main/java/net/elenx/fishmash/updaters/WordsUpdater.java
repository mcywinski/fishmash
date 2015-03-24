package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.OptionsActivity;

public class WordsUpdater extends FishmashUpdater
{
    private OptionsActivity optionsActivity;
    private int wordListId;

    public WordsUpdater(OptionsActivity optionsActivity, int wordListId)
    {
        this.optionsActivity = optionsActivity;
        this.wordListId = wordListId;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        publishProgress(CONNECTING);

        if(!optionsActivity.isOnline())
        {
            return null;
        }

        publishProgress(DOWNLOADING);

        publishProgress(CONVERTING);

        publishProgress(SAVING);

        return null;
    }
}
