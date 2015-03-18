package elenx.net.fishmash.activities;

import android.os.Bundle;
import android.widget.Button;

import elenx.net.fishmash.R;

public class ViewWordsActivity extends OptionsActivity
{
    private Button nextWordButton;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwords);

        nextWordButton = (Button) findViewById(R.id.nextWordButton);
    }
}
