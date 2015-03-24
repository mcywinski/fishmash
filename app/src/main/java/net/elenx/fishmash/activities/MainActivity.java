package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.elenx.fishmash.R;
import net.elenx.fishmash.updaters.WordListUpdater;

public class MainActivity extends OptionsActivity
{
    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void mainMenu()
    {
        // do not do anything, when main menu option has been selected
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WordListUpdater(this).execute();

        findViewById(R.id.viewWordsButton).setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getApplicationContext(), PickWordListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        );
    }
}
