package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamSummary;
import net.elenx.fishmash.updaters.SummaryUpdater;
import net.elenx.fishmash.updaters.listeners.SummaryListener;

public class SummaryActivity extends OptionsActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        attach(R.layout.summary);

        long examId = getIntent().getLongExtra(Fishmash.EXAM_ID, -1);

        if(examId <= 0)
        {
            learningAndExams();
        }

        SummaryUpdater summaryUpdater = new SummaryUpdater(this, examId);
        summaryUpdater.setSummaryListener
        (
            new SummaryListener()
            {
                @Override
                public void showSummary(ExamSummary[] examSummaries)
                {
                    if(examSummaries == null || examSummaries.length == 0)
                    {
                        learningAndExams();
                    }
                    else
                    {
                        buildTable(examSummaries);
                    }
                }
            }
        );

        summaryUpdater.execute();
    }

    private void buildTable(ExamSummary[] examSummaries)
    {
        TextView answer;
        CheckBox finished;
        CheckBox passed;
        TextView meaning;
        CheckBox examFinished;
        TextView phrase;

        TableLayout tableLayoutSummary = (TableLayout) findViewById(R.id.tableLayoutSummary);

        LayoutInflater layoutInflater = getLayoutInflater();

        for(ExamSummary examSummary : examSummaries)
        {
           TableLayout tableLayout = (TableLayout) layoutInflater.inflate(R.layout.fragment_summary, tableLayoutSummary);

            TableRow topRow = (TableRow) tableLayout.getChildAt(0);
            answer = (TextView) topRow.getChildAt(0);
            finished = (CheckBox) topRow.getChildAt(1);
            passed = (CheckBox) topRow.getChildAt(2);

            TableRow bottomRow = (TableRow) tableLayout.getChildAt(1);
            meaning = (TextView) bottomRow.getChildAt(0);
            examFinished = (CheckBox) topRow.getChildAt(1);
            phrase = (TextView) bottomRow.getChildAt(2);

            //
            tableLayoutSummary.addView(tableLayout);
            //

            answer.setText(examSummary.getAnswer());
            finished.setChecked(examSummary.isFinished());
            passed.setChecked(examSummary.isPassed());

            meaning.setText(examSummary.getMeaning());
            examFinished.setChecked(examSummary.isExamFinished());
            phrase.setText(examSummary.getPhrase());
        }
    }
}
