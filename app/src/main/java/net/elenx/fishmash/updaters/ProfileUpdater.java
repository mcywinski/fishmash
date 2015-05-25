package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.Profile;

public class ProfileUpdater extends FishmashUpdater
{
    private final UpdaterListener updaterListener;
    private Profile profile;

    public ProfileUpdater(OptionsActivity optionsActivity, UpdaterListener updaterListener)
    {
        super(optionsActivity);

        this.updaterListener = updaterListener;
    }

    @Override
    protected void download()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() <= 0)
        {
            // profile remains null, so we will take actions in save() with onFailure()

            return;
        }

        Authenticate authenticate = authenticateDAO.selectAll().get(0);

        String address = Constant.PROFILE + authenticate.getUserId();
        profile = restTemplate.getForObject(address, Profile.class, buildParameters());

    }

    @Override
    protected void save()
    {
        if(profile == null)
        {
            updaterListener.onFailure();

            return;
        }

        ProfileDAO profileDAO = new ProfileDAO(optionsActivity);
        profileDAO.truncate();
        profileDAO.insert(profile);

        updaterListener.onSuccess();
    }
}
