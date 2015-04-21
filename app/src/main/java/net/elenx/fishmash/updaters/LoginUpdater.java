package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.models.LoginPassword;
import net.elenx.fishmash.models.User;

import org.springframework.web.client.RestTemplate;

public class LoginUpdater extends FishmashUpdater
{
    private static final RestTemplate restTemplate = new RestTemplate();

    private User user;

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
            LoginPassword loginPassword = new LoginPassword(login, password);
            user = restTemplate.postForObject(Constant.AUTHENTICATE, loginPassword, User.class);
        }
        catch(Exception e)
        {
            // avoid null pointer
            user = new User();
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
