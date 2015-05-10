package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.SpeakingActivity;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.daos.WordsDAO;
import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.models.WordList;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LearningActivity extends SpeakingActivity
{
    private TextView phraseTextView;
    private TextView meaningTextView;

    private Locale phraseLocale;
    private Locale meaningLocale;

    private String phrase;
    private String meaning;

    private Button nextWordButton;

    private CheckBox speakCheckBox;
    private Button speakNowButton;

    private Iterator<Word> wordIterator;
    private List<Word> words;

    private Button showMeaningButton;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        initEverything();
    }

    private void initEverything()
    {
        attach(R.layout.activity_learning);

        long wordListId = getIntent().getLongExtra("wordListId", -1);

        if(wordListId <= 0)
        {
            return;
        }

        updateWords(wordListId);

        words = new WordsDAO(this).selectAll();

        if(words.size() < 1)
        {
            return;
        }

        WordList wordList = new WordListsDAO(this).select(wordListId);
        phraseLocale = wordList.getForeignLanguage().getLocale();
        meaningLocale = wordList.getMainLanguage().getLocale();

        speakNowButton = (Button) findViewById(R.id.speakNowButton);
        speakNowButton.setOnClickListener
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

        prepareForLearning();
        nextWordButton.performClick();
    }

    @Override
    public void onInit(int i)
    {
        super.onInit(i);

        if(isReadyToSpeak && speakCheckBox != null && speakNowButton != null)
        {
            speakCheckBox.setVisibility(View.VISIBLE);
            speakNowButton.setVisibility(View.VISIBLE);
        }
    }

    private void prepareForLearning()
    {
        phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        meaningTextView = (TextView) findViewById(R.id.meaningTextView);
        speakCheckBox = (CheckBox) findViewById(R.id.speakCheckBox);

        rewind();

        showMeaningButton = (Button) findViewById(R.id.showMeaningButton);
        showMeaningButton.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    meaningTextView.setText(meaning);
                    colorAccordingToSpeakAbility(meaningTextView, meaningLocale);
                }
            }
        );

        phraseTextView.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    showMeaningButton.performClick();
                }
            }
        );

        nextWordButton = (Button) findViewById(R.id.nextWordButton);
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

                        meaningTextView.setText("");

                        if(speakCheckBox.isChecked())
                        {
                            speakPhraseAndMeaning();
                        }
                    }
                    else
                    {
                        Toast.makeText(me, "Od początku", Toast.LENGTH_SHORT).show();
                        rewind();
                        nextWordButton.performClick();
                    }

                    if(isReadyToSpeak)
                    {
                        speakCheckBox.setVisibility(View.VISIBLE);
                        speakNowButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        );
    }

    private void speakPhraseAndMeaning()
    {
        say(phrase, phraseLocale);
        say(meaning, meaningLocale);
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

    private void rewind()
    {
        wordIterator = words.iterator();
    }
}
