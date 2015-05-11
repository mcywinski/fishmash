package net.elenx.fishmash.activities.core.drawer;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import net.elenx.fishmash.R;

import java.util.Arrays;
import java.util.List;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationDrawerCallbacks
{
    private static List<String> drawerItemPositionToResourceName;

    private LayoutInflater layoutInflater;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;

    private NavigationDrawerFragment navigationDrawerFragment;
    private FrameLayout container;
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        layoutInflater = LayoutInflater.from(this);
        container = (FrameLayout) findViewById(R.id.container);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp
        (
                drawerLayout
        );
    }

    private void restoreActionBar()
    {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        // instead of traditional menu - show drawer
        drawerLayout.openDrawer(Gravity.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if(!navigationDrawerFragment.isDrawerOpen())
        {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();

            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    private void attach(int resourceId)
    {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(resourceId, null);

        container.removeAllViews();
        container.addView(relativeLayout);
    }

    private void changeActionBarTitle(CharSequence title)
    {
        this.title = title;
        restoreActionBar();
    }

    protected void injectActivity(CharSequence title, int resourceId)
    {
        attach(resourceId);
        changeActionBarTitle(title);
    }

    protected int resourceIfOfPosition(int position)
    {
        if(drawerItemPositionToResourceName == null)
        {
            drawerItemPositionToResourceName = Arrays.asList(getResources().getStringArray(R.array.position_to_resource_name));
        }

        String resourceName = drawerItemPositionToResourceName.get(position);

        return getResources().getIdentifier(resourceName, "string", getPackageName());
    }
}
