package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.AuthenticateActivity;
import net.elenx.fishmash.activities.LearningActivity;
import net.elenx.fishmash.activities.LearningAndExamsActivity;
import net.elenx.fishmash.activities.ProfileActivity;
import net.elenx.fishmash.activities.core.drawer.NavigationDrawerFragment;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.utilities.Fishmash;

public abstract class OptionsActivity extends ActionsActivity
{
    protected final static String EMPTY_STRING = "";
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
    public void onNavigationDrawerItemSelected(int position)
    {
        switch(resourceIdOfPosition(position))
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Fishmash.REGISTER));
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

    @Override
    public void onKeyboardOpenedEvent()
    {
        Log.e("keyboard", "opened");
    }

    @Override
    public void onKeyboardClosedEvent()
    {
        Log.e("keyboard", "closed");
    }
}
