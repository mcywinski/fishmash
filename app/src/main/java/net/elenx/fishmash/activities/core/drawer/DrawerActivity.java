package net.elenx.fishmash.activities.core.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import net.elenx.fishmash.R;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationDrawerCallbacks
{
    private NavigationDrawerFragment navigationDrawerFragment;
    private LayoutInflater layoutInflater;
    private ActionBar actionBar;
    private boolean isFirstExecution = true;

    protected FrameLayout container;
    protected CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        layoutInflater = LayoutInflater.from(this);
        container = (FrameLayout) findViewById(R.id.container);

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp
        (
            R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.main_drawer_layout)
        );
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        Intent intent;

        switch(position)
        {
            case 0:
            case 2:
                intent = new Intent(this, NextActivity.class);
                break;
            case 1:
            default:
                intent = new Intent(this, OtherActivity.class);
                break;
        }

        if(isFirstExecution)
        {
            isFirstExecution = false;
        }
        else
        {
            startActivity(intent);
            finish();
        }
    }

    public void restoreActionBar()
    {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
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

    protected void attach(int resourceId)
    {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(resourceId, null);

        container.removeAllViews();
        container.addView(relativeLayout);
    }

    protected void changeActionBarTitle(CharSequence title)
    {
        this.title = title;
        restoreActionBar();
    }
}
