package net.elenx.fishmash.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.activities.core.drawer.NavigationDrawerFragment;
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.updaters.ExamUpdater;
import net.elenx.fishmash.updaters.WordListUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;
import net.elenx.fishmash.utilities.Fishmash;

import java.util.List;

public class LearningAndExamsActivity extends OptionsActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_learning_and_exams);

        updateWordLists();
    }

    private void updateWordLists()
    {
        WordListUpdater wordListUpdater = new WordListUpdater(this);
        wordListUpdater.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    updateExams();
                    showWordLists();
                }

                @Override
                public void onFailure()
                {
                    logout();
                }
            }
        );

        wordListUpdater.execute();
    }

    private void updateExams()
    {
        ExamUpdater examUpdater = new ExamUpdater(this);
        examUpdater.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    showExams();
                }

                @Override
                public void onFailure()
                {
                    logout();
                }
            }
        );

        examUpdater.execute();
    }

    // I am passing null as root - it's optional and I do not want to use it
    // inflated view should be attached as child to passed argument=root
    // and I have it's future parent (TableLayout) - but it tries to cast TableRow to TableLayout
    @SuppressLint("InflateParams")
    private void showWordLists()
    {
        WordListDAO wordListDAO = new WordListDAO(this);
        List<WordList> wordLists = wordListDAO.selectAll();

        TableLayout tableLayoutWordList = (TableLayout) findViewById(R.id.tableLayoutLearningSection);
        TableRow tableRow;
        TextView wordListName;
        TextView wordListFirstLanguage;
        TextView wordListSecondLanguage;

        LayoutInflater layoutInflater = getLayoutInflater();

        for(final WordList wordList : wordLists)
        {
            tableRow = (TableRow) layoutInflater.inflate(R.layout.fragment_wordlist, null);

            RelativeLayout relativeLayout = (RelativeLayout) tableRow.getChildAt(0);
            ImageView imageView = (ImageView) tableRow.getChildAt(1);

            wordListName = (TextView) relativeLayout.getChildAt(0);
            wordListFirstLanguage = (TextView) relativeLayout.getChildAt(1);
            wordListSecondLanguage = (TextView) relativeLayout.getChildAt(3);

            //
            tableLayoutWordList.addView(tableRow);
            //

            wordListName.setText(wordList.getName());
            wordListFirstLanguage.setText(wordList.getMainLanguage().getName());
            wordListSecondLanguage.setText(wordList.getForeignLanguage().getName());
            imageView.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        NavigationDrawerFragment.setCurrentSelectedPosition(0);
                        switchIntentTo(LearningActivity.class, Fishmash.WORD_LIST_ID, wordList.getId());
                    }
                }
            );
        }
    }

    // I am passing null as root - it's optional and I do not want to use it
    // inflated view should be attached as child to passed argument=root
    // and I have it's future parent (TableLayout) - but it tries to cast TableRow to TableLayout
    @SuppressLint("InflateParams")
    private void showExams()
    {
        ExamDAO examDAO = new ExamDAO(this);
        List<Exam> examList = examDAO.selectAll();

        TableLayout tableLayoutExams = (TableLayout) findViewById(R.id.tableLayoutExamSection);
        TableRow tableRow;
        TextView examName;
        TextView examDescription;
        boolean isFinished;

        LayoutInflater layoutInflater = getLayoutInflater();

        for(final Exam exam : examList)
        {
            tableRow = (TableRow) layoutInflater.inflate(R.layout.fragment_exam, null);

            RelativeLayout relativeLayout = (RelativeLayout) tableRow.getChildAt(0);
            ImageView imageView = (ImageView) tableRow.getChildAt(1);

            examName = (TextView) relativeLayout.getChildAt(0);
            examDescription = (TextView) relativeLayout.getChildAt(1);

            //
            tableLayoutExams.addView(tableRow);
            //

            isFinished = exam.isFinished();

            examName.setText(exam.getName());
            examDescription.setText(Fishmash.TO + exam.getDateExamFinish().inShortFormat());
            imageView.setImageResource(isFinished ? R.drawable.stats : R.drawable.main_exams_button);
            imageView.setContentDescription(getString(isFinished ? R.string.stats : R.string.exam));
            imageView.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        NavigationDrawerFragment.setCurrentSelectedPosition(0);
                        switchIntentTo(ExamActivity.class, Fishmash.EXAM_ID, exam.getId());
                    }
                }
            );
        }
    }
}
