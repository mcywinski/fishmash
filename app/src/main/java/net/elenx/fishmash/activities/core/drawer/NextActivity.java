package net.elenx.fishmash.activities.core.drawer;

import android.os.Bundle;

import net.elenx.fishmash.R;

public class NextActivity extends DrawerActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        injectActivity("Next", R.layout.activity_next);
    }
}
