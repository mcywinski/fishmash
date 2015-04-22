package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.elenx.fishmash.R;

public class MenuActivity extends OptionsActivity
{
    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void mainMenu()
    {
        // do not call super, because super redirects here
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.buttonViewWords).setOnClickListener
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

        findViewById(R.id.buttonShowOptions).setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    openOptionsMenu();
                }
            }
        );
    }
}
