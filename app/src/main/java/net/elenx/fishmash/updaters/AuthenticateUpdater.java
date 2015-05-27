package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.adapters.Credentials;
import net.elenx.fishmash.utilities.Fishmash;

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
    protected void download()
    {
        authenticate = fishmashRest.postForObject(Fishmash.AUTHENTICATE, new Credentials(login, password), Authenticate.class);
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
}
