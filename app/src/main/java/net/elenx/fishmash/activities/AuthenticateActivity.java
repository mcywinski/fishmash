package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
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
            pickWordList();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        attach(R.layout.actvity_authenticate);

        ImageView mainTopBar = (ImageView) findViewById(R.id.main_top_bar);
        mainTopBar.setVisibility(View.GONE);

        textViewFailedLogin = (TextView) findViewById(R.id.textViewFailedLogin);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        if(isOffline())
        {
            showOfflineWarning();
        }

        prepareButtonLogInListener();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        // disable whole drawer
    }

    private void prepareButtonLogInListener()
    {
        ImageView imageViewLogIn = (ImageView) findViewById(R.id.imageViewLogIn);

        imageViewLogIn.setOnClickListener
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
                                pickWordList();
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

    private void showOfflineWarning()
    {
        runOnUiThread
        (
            new Runnable()
            {
                @Override
                public void run()
                {
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
                    textViewFailedLogin.setText(getText(R.string.bad_credentials));
                }
            }
        );
    }

    private void hideWarning()
    {
        textViewFailedLogin.setText("");
    }
}
