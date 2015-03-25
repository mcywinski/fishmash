package net.elenx.fishmash.activities;

import android.os.Bundle;

public class MainActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        updateWordLists();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        mainMenu();
    }
}
