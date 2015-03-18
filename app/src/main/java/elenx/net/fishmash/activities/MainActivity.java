package elenx.net.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import elenx.net.fishmash.R;

public class MainActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.viewWordsButton).setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getApplicationContext(), ViewWordsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        );
    }
}
