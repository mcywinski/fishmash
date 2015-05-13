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
                switchIntentTo(LearningActivity.class);
                break;

            case R.string.pick_wordlist:
                pickWordList();
                break;

            case R.string.profile:
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

        return authenticateDAO.count() > 0;
    }

    protected void logout()
    {
        new AuthenticateDAO(this).truncate();

        NavigationDrawerFragment.setCurrentSelectedPositionToZero();

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

//        try
//        {
//            throw new Exception();
//        }
//        catch(Exception e)
//        {
//            Log.e(" ", " ", e);
//        }

        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }
}
