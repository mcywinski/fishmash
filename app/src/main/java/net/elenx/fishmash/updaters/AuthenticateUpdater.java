package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
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
    protected void download()
    {
        LoginPassword loginPassword = new LoginPassword(login, password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<String> httpEntity = new HttpEntity<>(loginPassword.toJson(), requestHeaders);

        try
        {
            authenticate = restTemplate.postForObject(Constant.API + Constant.AUTHENTICATE, httpEntity, Authenticate.class);
        }
        catch(Exception e)
        {
            Log.e(Constant.API + Constant.AUTHENTICATE, httpEntity.getBody(), e);
        }
    }

    @Override
    protected void convert()
    {

    }

    @Override
    protected void save()
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
