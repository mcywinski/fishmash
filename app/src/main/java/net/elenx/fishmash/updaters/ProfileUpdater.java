package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.Profile;
import net.elenx.fishmash.models.containers.UserIdToken;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ProfileUpdater extends FishmashUpdater
{
    private UpdaterListener updaterListener;
    private Profile profile;

    public ProfileUpdater(OptionsActivity optionsActivity, UpdaterListener updaterListener)
    {
        super(optionsActivity);

        this.updaterListener = updaterListener;
    }

    @Override
    public void download()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() <= 0)
        {
            // profile remains null, so we will take actions in save() with onFailure()
            return;
        }

        Authenticate authenticate = authenticateDAO.selectAll().get(0);

        UserIdToken userIdToken = new UserIdToken(authenticate.getUser_id(), authenticate.getToken());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<String> user = new HttpEntity<>(userIdToken.toJson(), requestHeaders);

        profile = restTemplate.postForObject(Constant.API + Constant.USER_INFO, user, Profile.class);
    }

    @Override
    public void convert()
    {

    }

    @Override
    public void save()
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
