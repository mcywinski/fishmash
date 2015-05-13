package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.AuthenticateActivity;
import net.elenx.fishmash.activities.LearningActivity;
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
            case R.string.learning:
                learning();
                break;

            case R.string.pick_wordlist:
                pickWordList();
                break;

            case R.string.profile:
                NavigationDrawerFragment.setCurrentSelectedPosition(2);
                switchIntentTo(ProfileActivity.class);
                break;

            case R.string.register:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.REGISTER));
                startActivity(browserIntent);
                break;

            case R.string.logout:
                logout();
                break;
        }
    }

    protected void learning()
    {
        NavigationDrawerFragment.setCurrentSelectedPosition(0);
        switchIntentTo(LearningActivity.class);
    }

    protected void pickWordList()
    {
        NavigationDrawerFragment.setCurrentSelectedPosition(1);
        switchIntentTo(PickWordListActivity.class);
    }

    protected void updateWords(long id)
    {
        new WordsUpdater(this, id).execute();
    }

    protected boolean isAuthenticated()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(this);

        return authenticateDAO.count() > 0;
    }

    protected void logout()
    {
        NavigationDrawerFragment.setCurrentSelectedPosition(1);

        new AuthenticateDAO(this).truncate();

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

        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }
}
