package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.widget.Button;

import net.elenx.fishmash.updaters.WordsUpdater;

import elenx.net.fishmash.R;

public class LearningActivity extends OptionsActivity
{
    private Button nextWordButton;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwords);

        nextWordButton = (Button) findViewById(R.id.nextWordButton);

        int wordListId = 1;

        WordsUpdater wordsUpdater = new WordsUpdater(this, wordListId);
    }
}
