package net.elenx.fishmash.activities;

import android.os.Bundle;

import net.elenx.fishmash.R;

public class ProfileActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);
    }
}
