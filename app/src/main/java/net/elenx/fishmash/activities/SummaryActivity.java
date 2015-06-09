package net.elenx.fishmash.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.updaters.ExamUpdater;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;
import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamSummary;
import net.elenx.fishmash.updaters.SummaryUpdater;
import net.elenx.fishmash.updaters.listeners.SummaryListener;

public class SummaryActivity extends OptionsActivity
{
    private long examId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.summary);

        examId = getIntent().getLongExtra(Fishmash.EXAM_ID, -1);

        if(examId <= 0)
        {
            learningAndExams();
        }

        final SummaryUpdater summaryUpdater = new SummaryUpdater(this, examId);
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

        ExamUpdater examUpdater = new ExamUpdater(this);
        examUpdater.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    buildTableHeader();
                    summaryUpdater.execute();
                }

                @Override
                public void onFailure()
                {
                    learningAndExams();
                }
            }
        );

        examUpdater.execute();
    }

    // I am passing null as root - it's optional and I do not want to use it
    // inflated view should be attached as child to passed argument=root
    // and I have it's future parent (TableLayout) - but it tries to cast TableRow to TableLayout
    @SuppressLint("InflateParams")
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
           TableLayout tableLayout = (TableLayout) layoutInflater.inflate(R.layout.fragment_summary, null);

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

    private void buildTableHeader()
    {
        Exam exam = new ExamDAO(this).select(examId);

        TextView examSummaryName = (TextView) findViewById(R.id.textViewExamSummaryName);
        examSummaryName.setText(exam.getName());

        TextView examSummaryDescription = (TextView) findViewById(R.id.textViewExamSummaryDescription);
        examSummaryDescription.setText("Summary");

        ImageView back = (ImageView) findViewById(R.id.imageViewBack);
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
}
