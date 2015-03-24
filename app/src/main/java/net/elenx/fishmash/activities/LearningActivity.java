package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.WordsDAO;
import net.elenx.fishmash.models.Word;

import java.util.Iterator;

public class LearningActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        updateWords(getIntent().getLongExtra("wordListId", -1));

        prepareForLearning();
    }

    private void prepareForLearning()
    {
        final TextView phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        final TextView meaningTextView = (TextView) findViewById(R.id.meaningTextView);

        final Iterator<Word> wordIterator = new WordsDAO(this).selectAll().iterator();

        Button nextWordButton = (Button) findViewById(R.id.nextWordButton);
        nextWordButton.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(wordIterator.hasNext())
                    {
                        Word word = wordIterator.next();

                        phraseTextView.setText(word.getPhrase());
                        meaningTextView.setText(word.getMeaning());
                    }
                    else
                    {
                        mainMenu();
                    }
                }
            }
        );

        // show first word
        nextWordButton.performClick();
    }
}
