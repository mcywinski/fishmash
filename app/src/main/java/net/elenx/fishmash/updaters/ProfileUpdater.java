package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.Profile;

public class ProfileUpdater extends FishmashUpdater
{
    private Profile profile;

    public ProfileUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
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

        String address = Constant.PROFILE + authenticate.getUser_id() + "?api_token=" + authenticate.getToken();
        Log.e("url", address);
        profile = fishmashRest.getForObject(address, Profile.class);
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
