package net.elenx.fishmash.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.updaters.AuthenticateUpdater;
import net.elenx.fishmash.updaters.UpdaterListener;

public class AuthenticateActivity extends OptionsActivity
{
    private TextView textViewFailedLogin;
    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        // do not call super, because super redirects here, when not logged in

        if(isAuthenticated())
        {
            mainMenu();
        }
    }

    private void showOfflineWarning()
    {
        runOnUiThread
        (
            new Runnable()
            {
                @Override
                public void run()
                {
                    textViewFailedLogin.setVisibility(View.VISIBLE);
                    textViewFailedLogin.setText(getText(R.string.offline));
                }
            }
        );
    }

    private void showBadCredentialsWarning()
    {
        runOnUiThread
        (
            new Runnable()
            {
                @Override
                public void run()
                {
                    textViewFailedLogin.setVisibility(View.VISIBLE);
                    textViewFailedLogin.setText(getText(R.string.bad_credentials));
                }
            }
        );
    }

    private void hideWarning()
    {
        textViewFailedLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.actvity_authenticate);

        textViewFailedLogin = (TextView) findViewById(R.id.textViewFailedLogin);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        if(isOffline())
        {
            showOfflineWarning();
        }

        prepareButtonLogInListener();
        prepareButtonRegisterListener();
    }

    private void prepareButtonRegisterListener()
    {
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shrouded-fjord-4731.herokuapp.com/users/register"));
                    startActivity(browserIntent);
                    finish();
                }
            }
        );
    }

    private void prepareButtonLogInListener()
    {
        Button buttonLogIn = (Button) findViewById(R.id.buttonLogIn);

        buttonLogIn.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(isOffline())
                    {
                        showOfflineWarning();

                        return;
                    }

                    hideWarning();

                    String login = editTextLogin.getText().toString();
                    String password = editTextPassword.getText().toString();

                    new AuthenticateUpdater
                    (
                        me,
                        login,
                        password,
                        new UpdaterListener()
                        {
                            @Override
                            public void onSuccess()
                            {
                                mainMenu();
                            }

                            @Override
                            public void onFailure()
                            {
                                showBadCredentialsWarning();
                            }
                        }
                    ).execute();
                }
            }
        );
    }
}
