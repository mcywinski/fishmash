package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.daos.WordsDAO;
import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.models.WordList;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LearningActivity extends SpeakingActivity
{
    private Locale phraseLocale;
    private Locale meaningLocale;

    private String phrase;
    private String meaning;

    private Button nextWordButton;

    private CheckBox speakCheckBox;
    private Button speakNowButton;

    private Iterator<Word> wordIterator;
    private List<Word> words;

    private LearningActivity me;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
    }

    private void initEverything()
    {
        setContentView(R.layout.activity_learning);

        me = this;

        long wordListId = getIntent().getLongExtra("wordListId", -1);
        updateWords(wordListId);

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
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        nextWordButton.performClick();
    }

    @Override
    public void onInit(int i)
    {
        super.onInit(i);
        initEverything();

        if(i == TextToSpeech.SUCCESS)
        {
            speakCheckBox.setVisibility(View.VISIBLE);
            speakNowButton.setVisibility(View.VISIBLE);
        }
    }

    private void prepareForLearning()
    {
        final TextView phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        final TextView meaningTextView = (TextView) findViewById(R.id.meaningTextView);
        speakCheckBox = (CheckBox) findViewById(R.id.speakCheckBox);

        words = new WordsDAO(this).selectAll();

        rewind();

        Button showMeaningButton = (Button) findViewById(R.id.showMeaningButton);
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
                        Toast.makeText(me, "Od nowa", Toast.LENGTH_SHORT).show();
                        rewind();
                        nextWordButton.performClick();
                    }
                }
            }
        );
    }

    private void speakPhraseAndMeaning()
    {
        if(!isOnline())
        {
            return;
        }

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

    private void rewind()
    {
        wordIterator = words.iterator();
    }
}
