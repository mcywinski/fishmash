package net.elenx.fishmash.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.SpeakingActivity;
import net.elenx.fishmash.daos.WordDAO;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.models.adapters.Language;
import net.elenx.fishmash.updaters.WordUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;
import net.elenx.fishmash.utilities.Cycle;
import net.elenx.fishmash.utilities.Fishmash;

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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_learning);
    }

    @Override
    public void onInit(int resultCode)
    {
        super.onInit(resultCode);

        concludeWordListId();
        updateWords();
    }

    private void updateWords()
    {
        WordUpdater wordUpdater = new WordUpdater(this, lastWordList);
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

        wordUpdater.execute();
    }

    private void concludeWordListId()
    {
        long wordListId = getIntent().getLongExtra(Fishmash.WORD_LIST_ID, -1);

        // if I have manually selected word list == if I have not came from drawer
        if(wordListId > 0)
        {
            // do not show me last one (unless explicitly asked)
            lastWordList = wordListId;
        }

        Log.e("lastWordList", String.valueOf(lastWordList));

        // sanity check - no matter how did I get here - it needs to be > 0 to continue
        if(lastWordList <= 0)
        {
            learningAndExams();
        }
    }

    private void prepareToLearning()
    {
        List<Word> words = new WordDAO(this).selectAll();

        Log.e("ta lista slowek ma ich", String.valueOf(words.size()));

        if(words.size() < 1)
        {
            learningAndExams();
        }

        cycle = new Cycle<>(words);

        WordList wordList = new WordListDAO(this).select(lastWordList);

        Language mainLanguage = wordList.getMainLanguage();
        Language foreignLanguage = wordList.getForeignLanguage();

        mainLanguageLocale = mainLanguage.getLocale();
        foreignLanguageLocale = foreignLanguage.getLocale();

        mainLanguageName = mainLanguage.getName();
        foreignLanguageName = foreignLanguage.getName();

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
        isMainLanguageActive = false;

        flip();
    }

    private void flip()
    {
        String buffer;

        if(isMainLanguageActive)
        {
            buffer = word.getMeaning();
            say(buffer, mainLanguageLocale);

            phraseXorMeaning.setTextColor(Color.GREEN);

            mainXorForeignLanguage.setText(mainLanguageName);
            mainXorForeignLanguage.setTextColor(Color.GREEN);

            tapToFlip.setText(getString(R.string.tap_to_show_phrase));
        }
        else
        {
            buffer = word.getPhrase();
            say(buffer, foreignLanguageLocale);

            phraseXorMeaning.setTextColor(Color.RED);

            mainXorForeignLanguage.setText(foreignLanguageName);
            mainXorForeignLanguage.setTextColor(Color.RED);

            tapToFlip.setText(getString(R.string.tap_to_show_meaning));
        }

        phraseXorMeaning.setText(buffer);
    }
}
