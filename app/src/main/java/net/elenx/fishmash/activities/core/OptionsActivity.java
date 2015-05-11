package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.AuthenticateActivity;
import net.elenx.fishmash.activities.MenuActivity;
import net.elenx.fishmash.activities.ProfileActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.updaters.WordListUpdater;
import net.elenx.fishmash.updaters.WordsUpdater;

public abstract class OptionsActivity extends ProgressDialogActivity
{
    protected final OptionsActivity me = this;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);

        if(!isAuthenticated())
        {
            logout();
        }
    }

    @Override
    public void onBackPressed()
    {
        onPause();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        switch(drawerItemPositionToResourceId(position))
        {
            case R.string.main_menu:
                mainMenu();
                break;

            case R.string.update_wordlists:
                new WordListUpdater(this).execute();
                break;

            case R.string.profile:
                switchIntentTo(ProfileActivity.class);
                break;

            case R.string.logout:
                logout();
                break;
        }
    }

    protected void mainMenu()
    {
        switchIntentTo(MenuActivity.class);
    }

    protected void updateWords(long id)
    {
        new WordsUpdater(this, id).execute();
    }

    protected boolean isAuthenticated()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(this);

        return authenticateDAO.selectAll().size() > 0;
    }

    protected void logout()
    {
        new AuthenticateDAO(this).truncate();

        switchIntentTo(AuthenticateActivity.class);
    }

    protected void switchIntentTo(Class<?> clazz)
    {
        Log.e(getClass().toString(), clazz.toString());

        Class myClass = getClass();

        if(myClass == clazz || myClass == AuthenticateActivity.class)
        {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }
}
