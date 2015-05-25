package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Profile;
import net.elenx.fishmash.updaters.ProfileUpdater;
import net.elenx.fishmash.updaters.UpdaterListener;

public class ProfileActivity extends OptionsActivity
{
    private TextView loginData;
    private TextView emailData;
    private TextView createdAtData;
    private TextView updatedAtData;

    @Override
    protected void onPostCreate(final Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        attach(R.layout.activity_profile);

        loginData = (TextView) findViewById(R.id.textViewLoginData);
        emailData = (TextView) findViewById(R.id.textViewEmailData);
        createdAtData = (TextView) findViewById(R.id.textViewCreatedAtData);
        updatedAtData = (TextView) findViewById(R.id.textViewUpdatedAtData);

        new ProfileUpdater
        (
            this,
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    showProfile();
                }

                @Override
                public void onFailure()
                {
                    logout();
                }
            }
        ).execute();
    }

    private void showProfile()
    {
        ProfileDAO profileDAO = new ProfileDAO(this);

        if(profileDAO.count() <= 0)
        {
            logout();

            return;
        }

        final Profile profile = profileDAO.selectAll().get(0);

        runOnUiThread
        (
            new Runnable()
            {
                @Override
                public void run()
                {
                    loginData.setText(profile.getLogin());
                    emailData.setText(profile.getEmail());
                    createdAtData.setText(profile.getCreatedAt());
                    updatedAtData.setText(profile.getUpdatedAt());
                }
            }
        );
    }
}
