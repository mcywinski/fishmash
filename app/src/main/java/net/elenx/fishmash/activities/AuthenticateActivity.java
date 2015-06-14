package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.updaters.AuthenticateUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;

public class AuthenticateActivity extends OptionsActivity
{
    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(isAuthenticated())
        {
            learningAndExams();
        }

        attach(R.layout.layout_authenticate);

        hideMainTopBar();
        bindViews();
        prepareButtonLogInListener();

        if(isOffline())
        {
            showOfflineWarning();
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    private void bindViews()
    {
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private void hideMainTopBar()
    {
        ImageView mainTopBar = (ImageView) findViewById(R.id.main_top_bar);
        mainTopBar.setVisibility(View.GONE);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        if(resourceIdOfPosition(position) == R.string.register)
        {
            super.onNavigationDrawerItemSelected(position);
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

                    AuthenticateUpdater authenticateUpdater = new AuthenticateUpdater(me, login, password);
                    authenticateUpdater.setUpdaterListener
                    (
                        new UpdaterListener()
                        {
                            @Override
                            public void onSuccess()
                            {
                                learningAndExams();
                                Log.e("otrzymany token to", new AuthenticateDAO(me).selectAll().get(0).getToken());
                            }

                            @Override
                            public void onFailure()
                            {
                                showBadCredentialsWarning();
                            }
                        }
                    );

                    authenticateUpdater.execute();
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
                    Toast.makeText(me, R.string.offline, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(me, R.string.bad_credentials, Toast.LENGTH_LONG).show();
                }
            }
        );
    }
}
