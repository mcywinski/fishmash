package net.elenx.fishmash.activities;

import android.graphics.Color;
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

    private ImageView nextWord;
    private ImageView previousWord;

    private TextView name;
    private TextView description;
    private ImageView back;

    private TextView phraseXorMeaning;
    private TextView mainXorForeignLanguage;
    private TextView tapToFlip;

    private Locale mainLanguageLocale;
    private Locale foreignLanguageLocale;

    private String mainLanguageName;
    private String foreignLanguageName;

    private WordList wordList;
    private Word word;
    private Cycle<Word> cycle;
    private boolean isMainLanguageActive;

    @Override
    public void onInit(int resultCode)
    {
        super.onInit(resultCode);

        attach(R.layout.layout_learning);
        concludeWordListId();
        showWords();
    }

    private void showWords()
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

    private void bindViews()
    {
        name = (TextView) findViewById(R.id.textViewWordListName);
        description = (TextView) findViewById(R.id.textViewWordListDescription);

        phraseXorMeaning = (TextView) findViewById(R.id.textViewQuestion);
        mainXorForeignLanguage = (TextView) findViewById(R.id.TextViewMainXorForeignLanguage);
        tapToFlip = (TextView) findViewById(R.id.textViewTapToFlip);

        nextWord = (ImageView) findViewById(R.id.imageViewNextWord);
        previousWord = (ImageView) findViewById(R.id.imageViewPreviousWord);
        back = (ImageView) findViewById(R.id.imageViewBack);
    }

    private void prepareToLearning()
    {
        List<Word> words = new WordDAO(this).selectAll();

        Log.e("ta lista slowek ma ich ", String.valueOf(words.size()));

        if(words.size() < 1)
        {
            learningAndExams();
        }

        cycle = new Cycle<>(words);

        prepareLanguages();
        prepareViews();

        nextWord.performClick();
    }

    private void prepareViews()
    {
        bindViews();

        name.setText(wordList.getName());
        description.setText(wordList.getDescription());

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

        nextWord.setOnClickListener
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

        previousWord.setOnClickListener
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

        back.setOnClickListener
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
    }

    private void prepareLanguages()
    {
        wordList = new WordListDAO(this).select(lastWordList);

        Language mainLanguage = wordList.getMainLanguage();
        Language foreignLanguage = wordList.getForeignLanguage();

        mainLanguageLocale = mainLanguage.getLocale();
        foreignLanguageLocale = foreignLanguage.getLocale();

        mainLanguageName = mainLanguage.getName();
        foreignLanguageName = foreignLanguage.getName();
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
