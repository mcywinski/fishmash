package net.elenx.fishmash.activities.core.drawer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

public class MyActionBarDrawerToggle extends ActionBarDrawerToggle
{
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerFragment navigationDrawerFragment;
    private FragmentActivity fragmentActivity;

    public MyActionBarDrawerToggle(NavigationDrawerFragment navigationDrawerFragment, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes)
    {
        super(navigationDrawerFragment.getActivity(), drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);

        this.navigationDrawerFragment = navigationDrawerFragment;
        this.fragmentActivity = navigationDrawerFragment.getActivity();
    }

    @Override
    public void onDrawerClosed(View drawerView)
    {
        super.onDrawerClosed(drawerView);

        if(!navigationDrawerFragment.isAdded())
        {
            return;
        }

        fragmentActivity.supportInvalidateOptionsMenu();
    }

    @Override
    public void onDrawerOpened(View drawerView)
    {
        super.onDrawerOpened(drawerView);

        if(!navigationDrawerFragment.isAdded())
        {
            return;
        }

        if(!navigationDrawerFragment.hasUserLearnedDrawer())
        {
            navigationDrawerFragment.setUserLearnedDrawer(true);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragmentActivity);
            sharedPreferences.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
        }

        fragmentActivity.supportInvalidateOptionsMenu();
    }
}
