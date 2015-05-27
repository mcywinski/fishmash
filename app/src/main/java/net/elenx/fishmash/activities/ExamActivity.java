package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.updaters.ExaminerListener;
import net.elenx.fishmash.updaters.Examiner;

public class ExamActivity extends OptionsActivity
{
    private Exam exam;
    private Examiner examiner;

    private TextView examName;
    private TextView examDescription;
    private ImageView back;
    private TextView question;
    private EditText answer;
    private ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.exam);

        long examId = getIntent().getLongExtra("examId", -1);

        Log.e("examId", String.valueOf(examId));

        if(examId <= 0)
        {
            Toast.makeText(this, "This exam has finished or have never existed", Toast.LENGTH_LONG).show();

            return;
        }

        exam = new ExamDAO(this).select(examId);

        bindViews();
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
                    Toast.makeText(me, "Exam is over", Toast.LENGTH_LONG).show();
                }
            }
        );

        examiner.execute();
    }

    private void bindViews()
    {
        examName = (TextView) findViewById(R.id.textViewExamName);
        examDescription = (TextView) findViewById(R.id.textViewExamDescription);
        back = (ImageView) findViewById(R.id.imageViewBack);
        question = (TextView) findViewById(R.id.textViewQuestion);
        answer = (EditText) findViewById(R.id.editTextAnswer);
        next = (ImageView) findViewById(R.id.imageViewNextWord);
    }

    private void prepareViews()
    {
        examName.setText(exam.getName());
        examDescription.setText("to " + exam.getDate_exam_finish().inShortFormat());

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
