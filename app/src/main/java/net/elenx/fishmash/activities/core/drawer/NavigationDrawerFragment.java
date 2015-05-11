package net.elenx.fishmash.activities.core.drawer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;

public class NavigationDrawerFragment extends Fragment
{
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static int currentSelectedPosition = 0;

    private NavigationDrawerCallbacks navigationDrawerCallbacks;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private View fragmentContainerView;

    private boolean fromSavedInstanceState;
    private static boolean userLearnedDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userLearnedDrawer = sharedPreferences.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if(savedInstanceState != null)
        {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            fromSavedInstanceState = true;
        }

        selectItem(currentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        drawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        drawerListView.setOnItemClickListener
        (
            new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    selectItem(position);
                }
            }
        );

        drawerListView.setAdapter
        (
            new ArrayAdapter<>
            (
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                getResources().getStringArray(R.array.position_to_resource_value)
            )
        );

        drawerListView.setItemChecked(currentSelectedPosition, true);

        return drawerListView;
    }

    public boolean isDrawerOpen()
    {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout)
    {
        fragmentContainerView = getActivity().findViewById(fragmentId);

        this.drawerLayout = drawerLayout;
        this.drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBarDrawerToggle = new MyActionBarDrawerToggle(this, this.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if( !(userLearnedDrawer || fromSavedInstanceState) )
        {
            this.drawerLayout.openDrawer(fragmentContainerView);
        }

        this.drawerLayout.post
        (
            new Runnable()
            {
                @Override
                public void run()
                {
                    actionBarDrawerToggle.syncState();
                }
            }
        );

        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void selectItem(int position)
    {
        currentSelectedPosition = position;

        if(drawerListView != null)
        {
            drawerListView.setItemChecked(position, true);
        }

        if(drawerLayout != null)
        {
            drawerLayout.closeDrawer(fragmentContainerView);
        }

        if(navigationDrawerCallbacks != null)
        {
            navigationDrawerCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            navigationDrawerCallbacks = (NavigationDrawerCallbacks) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        if(item.getItemId() == R.id.website)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.SERVER));
            startActivity(browserIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar()
    {
        return ( (AppCompatActivity) getActivity() ).getSupportActionBar();
    }

    public boolean hasUserLearnedDrawer()
    {
        return userLearnedDrawer;
    }

    public void setUserLearnedDrawer(boolean userLearnedDrawer)
    {
        NavigationDrawerFragment.userLearnedDrawer = userLearnedDrawer;
    }

    public static void setCurrentSelectedPosition(int currentSelectedPosition)
    {
        NavigationDrawerFragment.currentSelectedPosition = currentSelectedPosition;
    }
}
