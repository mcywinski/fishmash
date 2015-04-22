package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.LoginPassword;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthenticateUpdater extends FishmashUpdater
{
    private final String login;
    private final String password;
    private final UpdaterListener updaterListener;

    private Authenticate authenticate;

    public AuthenticateUpdater(OptionsActivity optionsActivity, String login, String password, UpdaterListener updaterListener)
    {
        super(optionsActivity);

        this.login = login;
        this.password = password;
        this.updaterListener = updaterListener;
    }

    @Override
    public void download()
    {
        try
        {
            LoginPassword loginPassword = new LoginPassword(login, password);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json"));

            HttpEntity<String> httpEntity = new HttpEntity<>(loginPassword.toJson(), requestHeaders);

            authenticate = restTemplate.postForObject(Constant.API + Constant.AUTHENTICATE, httpEntity, Authenticate.class);
        }
        catch(Exception ignored)
        {

        }
    }

    @Override
    public void convert()
    {

    }

    @Override
    public void save()
    {
        if(authenticate == null)
        {
            updaterListener.onFailure();
            return;
        }

        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);
        authenticateDAO.truncate();
        authenticateDAO.insert(authenticate);

        updaterListener.onSuccess();
    }
}
