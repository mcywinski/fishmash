package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginUpdater extends FishmashUpdater
{
    private OptionsActivity optionsActivity;
    private JSONObject jsonObjectUser;

    private String login;
    private String password;

    public LoginUpdater(OptionsActivity optionsActivity, String login, String password)
    {
        this.optionsActivity = optionsActivity;
        this.login = login;
        this.password = password;
    }

    @Override
    public void download()
    {
        try
        {
            jsonObjectUser = new JSONObject(getStringFrom(Constant.LISTS + Constant.AUTHENTICATE));
        }
        catch(JSONException e)
        {
            e.printStackTrace();

            // avoid null pointer
            jsonObjectUser = new JSONObject();
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
