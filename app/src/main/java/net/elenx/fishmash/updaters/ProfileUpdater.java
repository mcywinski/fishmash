package net.elenx.fishmash.updaters;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Profile;

public class ProfileUpdater extends FishmashUpdater
{
    private Profile profile;

    public ProfileUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void download() throws Exception
    {
        profile = fishmashRest.getForObject(Fishmash.USERS_USERID_TOKEN, Profile.class, buildParameters());
    }

    @Override
    protected void save() throws Exception
    {
        if(profile == null)
        {
            throw new Exception("profile is null");
        }

        ProfileDAO profileDAO = new ProfileDAO(optionsActivity);
        profileDAO.truncate();
        profileDAO.insert(profile);
    }
}
