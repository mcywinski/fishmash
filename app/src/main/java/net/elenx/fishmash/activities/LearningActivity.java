package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private static long lastWordList = -1;

    private TextView phraseTextView;
    private TextView meaningTextView;

    private Locale phraseLocale;
    private Locale meaningLocale;

    private String phrase;
    private String meaning;

    private ImageView imageViewPreviousWord;
    private ImageView imageViewNextWord;

    private Iterator<Word> wordIterator;
    private List<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initEverything();
    }

    private void initEverything()
    {
        attach(R.layout.activity_learning);

        long wordListId = getIntent().getLongExtra("wordListId", -1);

        Log.e("wordListId", String.valueOf(wordListId));

        if(wordListId <= 0)
        {
            if(lastWordList > -1)
            {
                wordListId = lastWordList;
            }
            else
            {
                Toast.makeText(this, "This wordlist is empty", Toast.LENGTH_LONG).show();

                return;
            }
        }
        else
        {
            lastWordList = wordListId;
        }

        updateWords(wordListId);

        words = new WordsDAO(this).selectAll();

        if(words.size() < 1)
        {
            Toast.makeText(this, "This wordlist is empty", Toast.LENGTH_LONG).show();

            return;
        }

        WordList wordList = new WordListsDAO(this).select(wordListId);
        phraseLocale = wordList.getForeignLanguage().getLocale();
        meaningLocale = wordList.getMainLanguage().getLocale();

        TextView wordListName = (TextView) findViewById(R.id.textViewWordListName);
        wordListName.setText(wordList.getName());

        TextView wordListDescription = (TextView) findViewById(R.id.textViewWordListDescription);
        wordListDescription.setText(wordList.getDescription());

        ImageView imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    pickWordList();
                }
            }
        );

        prepareForLearning();
        imageViewNextWord.performClick();
    }

    private void prepareForLearning()
    {
        phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        meaningTextView = (TextView) findViewById(R.id.meaningTextView);

        rewind();

        phraseTextView.setOnClickListener
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

        imageViewNextWord = (ImageView) findViewById(R.id.imageViewNextWord);
        imageViewNextWord.setOnClickListener
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

                        speakPhraseAndMeaning();
                    }
                    else
                    {
                        Toast.makeText(me, "Od początku", Toast.LENGTH_SHORT).show();
                        rewind();
                        imageViewNextWord.performClick();
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
