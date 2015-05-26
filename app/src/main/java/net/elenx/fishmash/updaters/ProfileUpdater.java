package net.elenx.fishmash.updaters;

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
    protected void download() throws Exception
    {
        String url = Constant.USERS + buildParameters();
        profile = fishmashRest.getForObject(url, Profile.class);
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

    private String buildParameters() throws Exception
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() <= 0)
        {
            throw new Exception("user is not logged in");
        }

        Authenticate authenticate = authenticateDAO.selectAll().get(0);

        return authenticate.getUser_id() + "?api_token=" + authenticate.getToken();
    }
}
