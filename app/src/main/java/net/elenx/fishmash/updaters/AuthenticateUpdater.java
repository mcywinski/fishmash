package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.LoginPassword;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class AuthenticateUpdater extends FishmashUpdater
{
    private static final RestTemplate restTemplate = new RestTemplate();

    private Authenticate authenticate;

    private String login;
    private String password;
    private AuthenticationListener authenticationListener;

    public AuthenticateUpdater(OptionsActivity optionsActivity, String login, String password, AuthenticationListener authenticationListener)
    {
        super(optionsActivity);

        this.login = login;
        this.password = password;
        this.authenticationListener = authenticationListener;
    }

    @Override
    public void download()
    {
        try
        {
            LoginPassword loginPassword = new LoginPassword(login, password);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json"));
            HttpEntity<String> user = new HttpEntity<>(loginPassword.toJson(), requestHeaders);

            authenticate = restTemplate.postForObject(Constant.API + Constant.AUTHENTICATE, user, Authenticate.class);
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
            authenticationListener.onFailure();
            return;
        }

        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);
        authenticateDAO.truncate();
        authenticateDAO.insert(authenticate);

        authenticationListener.onSuccess();
    }
}
