package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.adapters.PasswordResult;
import net.elenx.fishmash.models.adapters.PasswordsHolder;
import net.elenx.fishmash.updaters.listeners.PasswordListener;
import net.elenx.fishmash.utilities.Fishmash;

public class PasswordUpdater extends FishmashUpdater
{
    private PasswordsHolder passwordsHolder;
    private PasswordListener passwordListener;
    private PasswordResult passwordResult;

    public PasswordUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        if(passwordListener == null || passwordResult == null)
        {
            return;
        }

        switch((int) passwordResult.getResultStatus())
        {
            case -2:
                passwordListener.oldPasswordIsWrong();
                break;
            case -1:
                passwordListener.passwordsAreDifferent();
                break;
            case 0: // this case is same as default, but I will leave it for readability and as API documentation
                passwordListener.internalError();
                break;
            case 1:
                passwordListener.passwordChanged();
                break;
            default:
                passwordListener.internalError();
        }
    }

    @Override
    void download() throws Exception
    {
        passwordResult = REST_INTERCEPTOR.postForObject(Fishmash.SET_PASSWORD, passwordsHolder, PasswordResult.class, buildParameters());
    }

    public void setPasswordsHolder(PasswordsHolder passwordsHolder)
    {
        this.passwordsHolder = passwordsHolder;
    }

    public void setPasswordListener(PasswordListener passwordListener)
    {
        this.passwordListener = passwordListener;
    }
}
