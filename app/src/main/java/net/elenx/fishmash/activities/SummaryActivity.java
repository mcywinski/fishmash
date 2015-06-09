package net.elenx.fishmash.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.models.ExamSummary;
import net.elenx.fishmash.updaters.ExamUpdater;
import net.elenx.fishmash.updaters.SummaryUpdater;
import net.elenx.fishmash.updaters.listeners.SummaryListener;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;
import net.elenx.fishmash.utilities.Fishmash;

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
        TextView question;
        TextView userInput;
        TextView result;
        TextView correctAnswer;
        boolean isAnswerCorrect;

        TableLayout tableLayoutSummary = (TableLayout) findViewById(R.id.tableLayoutSummary);

        LayoutInflater layoutInflater = getLayoutInflater();

        for(ExamSummary examSummary : examSummaries)
        {
            TableLayout tableLayout = (TableLayout) layoutInflater.inflate(R.layout.fragment_summary, null);

            TableRow firstRow = (TableRow) tableLayout.getChildAt(0);
            TableRow secondRow = (TableRow) tableLayout.getChildAt(1);

            RelativeLayout questionLayout = (RelativeLayout) firstRow.getChildAt(0);
            question = (TextView) questionLayout.getChildAt(0);

            RelativeLayout resultLayout = (RelativeLayout) firstRow.getChildAt(1);
            result = (TextView) resultLayout.getChildAt(0);

            RelativeLayout userInputLayout = (RelativeLayout) secondRow.getChildAt(0);
            userInput = (TextView) userInputLayout.getChildAt(0);

            RelativeLayout correctAnswerLayout = (RelativeLayout) secondRow.getChildAt(1);
            correctAnswer = (TextView) correctAnswerLayout.getChildAt(0);

            //
            tableLayoutSummary.addView(tableLayout);
            //

            isAnswerCorrect = examSummary.isPassed();

            question.setText(examSummary.getMeaning());
            userInput.setText(examSummary.getAnswer());

            result.setText(isAnswerCorrect ? getString(R.string.passed) : getString(R.string.failed));
            result.setTextColor(isAnswerCorrect ? Color.GREEN : Color.RED);

            correctAnswer.setText(examSummary.getPhrase());
            correctAnswer.setTextColor(isAnswerCorrect ? Color.GREEN : Color.RED);
        }
    }

    private void buildTableHeader()
    {
        Exam exam = new ExamDAO(this).select(examId);

        TextView examSummaryName = (TextView) findViewById(R.id.textViewExamSummaryName);
        examSummaryName.setText(exam.getName());

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
