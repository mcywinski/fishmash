package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.OptionsActivity;

public class ProfileUpdater extends FishmashUpdater
{
    private UpdaterListener updaterListener;

    ProfileUpdater(OptionsActivity optionsActivity, UpdaterListener updaterListener)
    {
        super(optionsActivity);

        this.updaterListener = updaterListener;
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
