package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.models.LoginPassword;
import net.elenx.fishmash.models.User;

import org.springframework.web.client.RestTemplate;

public class LoginUpdater extends FishmashUpdater
{
    private OptionsActivity optionsActivity;
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
            user = new RestTemplate().postForObject(Constant.AUTHENTICATE, new LoginPassword(login, password), User.class);
            Log.e("udalo", "sie");
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
