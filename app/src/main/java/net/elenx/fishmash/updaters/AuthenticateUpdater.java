package net.elenx.fishmash.updaters;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.adapters.Credentials;
import net.elenx.fishmash.utilities.Fishmash;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthenticateUpdater extends FishmashUpdater
{
    private final String login;
    private final String password;

    private Authenticate authenticate;

    public AuthenticateUpdater(OptionsActivity optionsActivity, String login, String password)
    {
        super(optionsActivity);

        this.login = login;
        this.password = password;
    }

    @Override
    protected void download() throws JsonProcessingException
    {
        Credentials credentials = new Credentials(login, password);

        authenticate = fishmashRest.postForObject(Fishmash.AUTHENTICATE, buildEntityWith(credentials), Authenticate.class);
    }

    @Override
    protected void save() throws Exception
    {
        if(authenticate == null)
        {
            throw new Exception("authenticate is null");
        }

        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);
        authenticateDAO.truncate();
        authenticateDAO.insert(authenticate);
    }

    private HttpEntity<String> buildEntityWith(Credentials credentials)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));

        return new HttpEntity<>(credentials.toJson(), requestHeaders);
    }
}
