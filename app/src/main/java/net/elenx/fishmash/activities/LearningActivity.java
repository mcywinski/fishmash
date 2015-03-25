package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.daos.WordsDAO;
import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.models.WordList;

import java.util.Iterator;
import java.util.Locale;

public class LearningActivity extends SpeakingActivity
{
    private Locale phraseLocale;
    private Locale meaningLocale;

    private String phrase;
    private String meaning;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        long wordListId = getIntent().getLongExtra("wordListId", -1);
        updateWords(wordListId);

        WordList wordList = new WordListsDAO(this).select(wordListId);
        phraseLocale = wordList.getForeignLanguage().getLocale();
        meaningLocale = wordList.getMainLanguage().getLocale();

        findViewById(R.id.speakNowButton).setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    speakPhraseAndMeaning();
                }
            }
        );
    }

    @Override
    public void onInit(int status)
    {
        prepareForLearning();
    }

    private void prepareForLearning()
    {
        final TextView phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        final TextView meaningTextView = (TextView) findViewById(R.id.meaningTextView);
        final CheckBox speakCheckBox = (CheckBox) findViewById(R.id.speakCheckBox);

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

                        phrase = word.getPhrase();
                        meaning = word.getMeaning();

                        phraseTextView.setText(phrase);
                        colorAccordingToSpeakAbility(phraseTextView, phraseLocale);

                        meaningTextView.setText(meaning);
                        colorAccordingToSpeakAbility(meaningTextView, meaningLocale);

                        if(speakCheckBox.isChecked())
                        {
                            speakPhraseAndMeaning();
                        }
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

    private void speakPhraseAndMeaning()
    {
        speak(phrase, phraseLocale);
        speak(meaning, meaningLocale);
    }

    private void colorAccordingToSpeakAbility(TextView textView, Locale locale)
    {
        if(locale == null)
        {
            textView.setTextColor(Color.RED);
        }
        else
        {
            textView.setTextColor(Color.GREEN);
        }
    }
}
