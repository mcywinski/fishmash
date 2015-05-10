package net.elenx.fishmash.activities.core.drawer;

import android.os.Bundle;

import net.elenx.fishmash.R;

public class OtherActivity extends DrawerActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        injectActivity("Other", R.layout.activity_other);
    }
}
