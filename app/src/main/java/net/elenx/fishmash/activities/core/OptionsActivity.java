package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.AuthenticateActivity;
import net.elenx.fishmash.activities.PickWordListActivity;
import net.elenx.fishmash.activities.ProfileActivity;
import net.elenx.fishmash.activities.core.drawer.NavigationDrawerFragment;
import net.elenx.fishmash.daos.AuthenticateDAO;
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
        finish();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        switch(resourceIfOfPosition(position))
        {
            case R.string.pick_wordlist:
                pickWordList();
                break;

            case R.string.profile:
                switchIntentTo(ProfileActivity.class);
                break;

            case R.string.logout:
                logout();
                break;
        }
    }

    protected void pickWordList()
    {
        switchIntentTo(PickWordListActivity.class);
    }

    protected void updateWords(long id)
    {
        new WordsUpdater(this, id).execute();
    }

    protected boolean isAuthenticated()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(this);

        Log.e("isAuthenticated", authenticateDAO.count() > 0 ? "tak" : "nie");

        return authenticateDAO.count() > 0;
    }

    protected void logout()
    {
        new AuthenticateDAO(this).truncate();

        NavigationDrawerFragment.setCurrentSelectedPosition(0);

        switchIntentTo(AuthenticateActivity.class);
    }

    protected void switchIntentTo(Class<?> clazz)
    {
        Class myClass = getClass();

        if(myClass == clazz)
        {
            return;
        }

        Log.e(myClass.toString(), clazz.toString());

        if(myClass == PickWordListActivity.class && clazz == AuthenticateActivity.class)
        {
            try
            {
                throw new Exception();
            }
            catch(Exception e)
            {
                Log.e("pickword", "authenticate", e);
            }
        }

        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }
}
