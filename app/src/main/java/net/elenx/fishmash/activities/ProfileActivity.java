package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ProfileDAO;
import net.elenx.fishmash.models.Profile;
import net.elenx.fishmash.models.adapters.FishmashCalendar;
import net.elenx.fishmash.updaters.ProfileUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;

public class ProfileActivity extends OptionsActivity
{
    private final static int LENGTH = 20;
    private final static String DOTS = "...";

    private TextView loginData;
    private TextView emailData;
    private TextView createdAtData;
    private TextView updatedAtData;
    private ImageView changePassword;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_profile);

        bindViews();

        changePassword.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    switchIntentTo(ChangePasswordActivity.class);
                }
            }
        );

        queryData();
    }

    private void queryData()
    {
        ProfileUpdater profileUpdater = new ProfileUpdater(this);
        profileUpdater.setUpdaterListener
        (
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
        );

        profileUpdater.execute();
    }

    private void bindViews()
    {
        loginData = (TextView) findViewById(R.id.textViewLoginData);
        emailData = (TextView) findViewById(R.id.textViewEmailData);
        createdAtData = (TextView) findViewById(R.id.textViewCreatedAtData);
        updatedAtData = (TextView) findViewById(R.id.textViewUpdatedAtData);
        changePassword = (ImageView) findViewById(R.id.imageViewChangePassword);
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
                    String login = profile.getLogin();
                    if(login.length() > LENGTH)
                    {
                        login = login.substring(0, LENGTH) + DOTS;
                    }

                    String email = profile.getEmail();
                    if(email.length() > LENGTH)
                    {
                        email = email.substring(0, LENGTH) + DOTS;
                    }

                    FishmashCalendar createdAt = new FishmashCalendar(profile.getCreatedAt());
                    FishmashCalendar updatedAt = new FishmashCalendar(profile.getUpdatedAt());

                    loginData.setText(login);
                    emailData.setText(email);
                    createdAtData.setText(createdAt.inShortFormat());
                    updatedAtData.setText(updatedAt.inShortFormat());
                }
            }
        );
    }
}
