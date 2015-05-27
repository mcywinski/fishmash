package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.utilities.Cycle;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.SpeakingActivity;
import net.elenx.fishmash.daos.WordDAO;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.updaters.WordUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;

import java.util.List;
import java.util.Locale;

public class LearningActivity extends SpeakingActivity
{
    private static long lastWordList = -1;

    private TextView phraseXorMeaning;
    private TextView mainXorForeignLanguage;
    private TextView tapToFlip;

    private Locale mainLanguageLocale;
    private Locale foreignLanguageLocale;

    private String mainLanguageName;
    private String foreignLanguageName;

    private Cycle<Word> cycle;

    private Word word;
    private boolean isMainLanguageActive;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.learning);

        long wordListId = getIntent().getLongExtra(Fishmash.WORD_LIST_ID, -1);

        if(wordListId <= 0 && lastWordList <= 0)
        {
            Toast.makeText(this, getString(R.string.emptyWordList), Toast.LENGTH_LONG).show();
            learningAndExams();
        }

        lastWordList = wordListId;

        WordUpdater wordUpdater = new WordUpdater(this, wordListId);
        wordUpdater.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    prepareToLearning();
                }

                @Override
                public void onFailure()
                {
                    learningAndExams();
                }
            }
        );
    }

    private void prepareToLearning()
    {
        List<Word> words = new WordDAO(this).selectAll();

        if(words.size() < 1)
        {
            Toast.makeText(this, getString(R.string.emptyWordList), Toast.LENGTH_LONG).show();

            return;
        }

        cycle = new Cycle<>(words);

        WordList wordList = new WordListDAO(this).select(lastWordList);

        mainLanguageLocale = wordList.getMainLanguage().getLocale();
        foreignLanguageLocale = wordList.getForeignLanguage().getLocale();

        mainLanguageName = mainLanguageLocale.getDisplayLanguage();
        foreignLanguageName = foreignLanguageLocale.getDisplayLanguage();

        TextView wordListName = (TextView) findViewById(R.id.textViewWordListName);
        wordListName.setText(wordList.getName());

        TextView wordListDescription = (TextView) findViewById(R.id.textViewWordListDescription);
        wordListDescription.setText(wordList.getDescription());

        phraseXorMeaning = (TextView) findViewById(R.id.textViewQuestion);
        mainXorForeignLanguage = (TextView) findViewById(R.id.TextViewMainXorForeignLanguage);
        tapToFlip = (TextView) findViewById(R.id.textViewTapToFlip);

        phraseXorMeaning.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    isMainLanguageActive = !isMainLanguageActive;
                    flip();
                }
            }
        );

        ImageView imageViewNextWord = (ImageView) findViewById(R.id.imageViewNextWord);
        imageViewNextWord.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    display(cycle.next());
                }
            }
        );

        ImageView imageViewPreviousWord = (ImageView) findViewById(R.id.imageViewPreviousWord);
        imageViewPreviousWord.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    display(cycle.previous());
                }
            }
        );

        ImageView imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    learningAndExams();
                }
            }
        );

        imageViewNextWord.performClick();
    }

    private void display(Word word)
    {
        this.word = word;
        isMainLanguageActive = true;

        flip();
    }

    private void flip()
    {
        String buffer;

        if(isMainLanguageActive)
        {
            buffer = word.getPhrase();
            say(buffer, mainLanguageLocale);

            phraseXorMeaning.setTextColor(Color.GREEN);

            mainXorForeignLanguage.setText(mainLanguageName);
            mainXorForeignLanguage.setTextColor(Color.GREEN);

            tapToFlip.setText(getString(R.string.tap_to_show_meaning));
        }
        else
        {
            buffer = word.getMeaning();
            say(buffer, foreignLanguageLocale);

            phraseXorMeaning.setTextColor(Color.RED);

            mainXorForeignLanguage.setText(foreignLanguageName);
            mainXorForeignLanguage.setTextColor(Color.RED);

            tapToFlip.setText(getString(R.string.tap_to_show_phrase));
        }

        phraseXorMeaning.setText(buffer);
    }
}
