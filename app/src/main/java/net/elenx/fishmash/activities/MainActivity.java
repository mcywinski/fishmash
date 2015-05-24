package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;

import net.elenx.fishmash.activities.core.OptionsActivity;

public class MainActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if(isAuthenticated())
        {
            Log.e("authenticated", "yes");
            pickWordList();
        }
        else
        {
            Log.e("authenticated", "no");
            switchIntentTo(AuthenticateActivity.class);
        }
    }
}
