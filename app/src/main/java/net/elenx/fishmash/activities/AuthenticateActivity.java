package net.elenx.fishmash.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.updaters.AuthenticateUpdater;
import net.elenx.fishmash.updaters.UpdaterListener;

public class AuthenticateActivity extends OptionsActivity
{
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
        if(resourceIfOfPosition(position) == R.string.register)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.REGISTER));
            startActivity(browserIntent);
        }
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
                    Toast.makeText(me, getText(R.string.offline), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(me, getText(R.string.bad_credentials), Toast.LENGTH_LONG).show();
                }
            }
        );
    }
}
