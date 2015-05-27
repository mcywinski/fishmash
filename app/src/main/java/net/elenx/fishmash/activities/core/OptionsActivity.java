package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.AuthenticateActivity;
import net.elenx.fishmash.activities.LearningActivity;
import net.elenx.fishmash.activities.LearningAndExamsActivity;
import net.elenx.fishmash.activities.ProfileActivity;
import net.elenx.fishmash.activities.core.drawer.NavigationDrawerFragment;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.updaters.WordUpdater;

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
            case R.string.last_word_list:
                learning();
                break;

            case R.string.learning_and_exams:
                learningAndExams();
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

    private void learning()
    {
        NavigationDrawerFragment.setCurrentSelectedPosition(0);
        switchIntentTo(LearningActivity.class);
    }

    // this is public only because XML onClick requires it
    // this parameter is useless - but is required to be called from XML
    @SuppressWarnings({"UnusedParameters", "unused"})
    public void learningAndExams(View view)
    {
        learningAndExams();
    }

    protected void learningAndExams()
    {
        NavigationDrawerFragment.setCurrentSelectedPosition(1);
        switchIntentTo(LearningAndExamsActivity.class);
    }

    protected void updateWords(long id)
    {
        new WordUpdater(this, id).execute();
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
        switchIntentTo(clazz, null, -1);
    }

    protected void switchIntentTo(Class<?> clazz, String extraKey, long extraValue)
    {
        Class myClass = getClass();

        if(myClass == clazz)
        {
            return;
        }

        Log.e(myClass.getSimpleName() + " is switching to", clazz.getSimpleName());

        Intent intent = new Intent(getApplicationContext(), clazz);

        intent.putExtra(extraKey, extraValue);
        Log.e(extraKey, String.valueOf(extraValue));

        startActivity(intent);
        finish();
    }
}
