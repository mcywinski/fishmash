package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.widget.Button;

import net.elenx.fishmash.R;

public class LearningActivity extends OptionsActivity
{
    private Button nextWordButton;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        updateWords(getIntent().getLongExtra("wordListId", -1));

        nextWordButton = (Button) findViewById(R.id.nextWordButton);
    }
}
