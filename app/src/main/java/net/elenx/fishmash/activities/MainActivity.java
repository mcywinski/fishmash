package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if(isAuthenticated())
        {
            mainMenu();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        // override default action (go to menu) to forbid login skip
        onPause();
    }
}
