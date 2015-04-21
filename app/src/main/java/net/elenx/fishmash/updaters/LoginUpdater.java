package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.LoginPassword;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class LoginUpdater extends FishmashUpdater
{
    private static final RestTemplate restTemplate = new RestTemplate();

    private Authenticate authenticate;

    private String login;
    private String password;

    public LoginUpdater(OptionsActivity optionsActivity, String login, String password)
    {
        super(optionsActivity);

        this.login = login;
        this.password = password;
    }

    @Override
    public void download()
    {
        try
        {
            LoginPassword loginPassword = new LoginPassword("android1", "1android");

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json"));
            HttpEntity<String> user = new HttpEntity<>(loginPassword.toJson(), requestHeaders);

            authenticate = restTemplate.postForObject(Constant.API + Constant.AUTHENTICATE, user, Authenticate.class);
        }
        catch(Exception e)
        {
            // avoid null pointer
            authenticate = new Authenticate();
            e.printStackTrace();
        }
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
