package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.ProfileDAO;

public class ProfileActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        ProfileDAO profileDAO = new ProfileDAO(this);

//        if(profileDAO.count() <= 0)
//        {
//            logout();
//        }

        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView loginData = (TextView) findViewById(R.id.textViewLoginData);
        TextView emailData = (TextView) findViewById(R.id.textViewEmailData);
        TextView createdAtData = (TextView) findViewById(R.id.textViewCreatedAtData);
        TextView updatedAtData = (TextView) findViewById(R.id.textViewUpdatedAtData);

//        Profile profile = profileDAO.selectAll().get(0);
//
//        loginData.setText(profile.getLogin());
//        emailData.setText(profile.getEmail());
//        createdAtData.setText(profile.getCreated_at());
//        updatedAtData.setText(profile.getUpdated_at());
    }
}
