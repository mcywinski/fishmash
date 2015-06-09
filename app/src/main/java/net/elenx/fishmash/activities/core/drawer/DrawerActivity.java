package net.elenx.fishmash.activities.core.drawer;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import net.elenx.fishmash.R;

import java.util.Arrays;
import java.util.List;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationDrawerCallbacks
{
    private static List<String> drawerItemPositionToResourceName;

    private LayoutInflater layoutInflater;
    private DrawerLayout drawerLayout;

    private NavigationDrawerFragment navigationDrawerFragment;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        layoutInflater = LayoutInflater.from(this);
        container = (FrameLayout) findViewById(R.id.container);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp(drawerLayout);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        // instead of traditional menu - show drawer
        drawerLayout.openDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return !navigationDrawerFragment.isDrawerOpen() || super.onCreateOptionsMenu(menu);
    }

    protected void attach(int resourceId)
    {
        View view = layoutInflater.inflate(resourceId, null);

        container.removeAllViews();
        container.addView(view);
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
