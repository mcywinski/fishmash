package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.updaters.listeners.ExaminerListener;
import net.elenx.fishmash.updaters.Examiner;

public class ExamActivity extends OptionsActivity
{
    private Exam exam;
    private Examiner examiner;

    private TextView examName;
    private TextView examDescription;
    private TextView question;
    private EditText answer;
    private ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.exam);

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

        final long examId = getIntent().getLongExtra(Fishmash.EXAM_ID, -1);

        if(examId <= 0)
        {
            learningAndExams();
        }

        exam = new ExamDAO(this).select(examId);

        prepareViews();

        examiner = new Examiner(this, examId);
        examiner.setExaminerListener
        (
            new ExaminerListener()
            {
                @Override
                public void prepareQuestion(String nextQuestion)
                {
                    question.setText(nextQuestion);
                }

                @Override
                public void examFinished()
                {
                    Toast.makeText(me, getString(R.string.examIsOver), Toast.LENGTH_LONG).show();
                    switchIntentTo(SummaryActivity.class, Fishmash.EXAM_ID, examId);
                }
            }
        );

        examiner.execute();
    }

    private void bindViews()
    {
        examName = (TextView) findViewById(R.id.textViewExamName);
        examDescription = (TextView) findViewById(R.id.textViewExamDescription);
        question = (TextView) findViewById(R.id.textViewQuestion);
        answer = (EditText) findViewById(R.id.editTextAnswer);
        next = (ImageView) findViewById(R.id.imageViewNextWord);
    }

    private void prepareViews()
    {
        bindViews();

        examName.setText(exam.getName());
        examDescription.setText(Fishmash.TO + exam.getDateExamFinish().inShortFormat());

        next.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    examiner.setAnswer(answer.getText().toString());
                    examiner.execute();
                }
            }
        );
    }
}
