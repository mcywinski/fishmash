package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.OptionsActivity;

public class WordsUpdater extends FishmashUpdater
{
    private int wordListId;

    public WordsUpdater(OptionsActivity optionsActivity, int wordListId)
    {
        this.optionsActivity = optionsActivity;
        this.wordListId = wordListId;
    }

    @Override
    public void download()
    {

    }

    @Override
    public void convert()
    {

    }

    @Override
    public void save()
    {

    }
}
