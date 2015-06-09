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
import net.elenx.fishmash.updaters.ExamQuestionProvider;
import net.elenx.fishmash.updaters.ExamStarter;
import net.elenx.fishmash.updaters.listeners.ExamQuestionListener;
import net.elenx.fishmash.updaters.listeners.UpdaterListener;
import net.elenx.fishmash.utilities.Fishmash;

public class ExamActivity extends OptionsActivity
{
    private final static String EMPTY_STRING = "";

    private Exam exam;
    private long examId;
    private ExamQuestionListener examQuestionListener;
    private boolean shouldSendAnswer = false;

    private TextView examName;
    private TextView examDescription;
    private TextView question;
    private EditText answer;
    private ImageView next;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.exam);

        prepareExamObjects();
        prepareViews();
        startExam();
    }

    private void prepareExamObjects()
    {
        examId = getIntent().getLongExtra(Fishmash.EXAM_ID, -1);

        if(examId <= 0)
        {
            learningAndExams();
        }

        exam = new ExamDAO(this).select(examId);

        examQuestionListener = new ExamQuestionListener()
        {
            @Override
            public void prepareQuestion(final String nextQuestion)
            {
               runOnUiThread
               (
                   new Runnable()
                   {
                       @Override
                       public void run()
                       {
                            question.setText(nextQuestion);
                       }
                   }
               );
            }

            @Override
            public void examFinished()
            {
                next.setEnabled(false);
                Toast.makeText(me, getString(R.string.examIsOver), Toast.LENGTH_LONG).show();
            }
        };
    }

    private void prepareViews()
    {
        bindViews();

        examName.setText(exam.getName());
        examDescription.setText(Fishmash.TO + exam.getDateExamFinish().inShortFormat());

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
                    try
                    {
                        ExamQuestionProvider examQuestionProvider = new ExamQuestionProvider(me, examId);
                        examQuestionProvider.setExamQuestionListener(examQuestionListener);
                        examQuestionProvider.setAnswer(fetchUserAnswer());

                        // skip sending answer only one time - on the beginning
                        examQuestionProvider.setShouldSendAnswer(shouldSendAnswer);
                        shouldSendAnswer = true;

                        examQuestionProvider.execute();
                    }
                    catch(Exception e)
                    {
                        Log.e("", "", e);
                    }
                }
            }
        );
    }

    private void bindViews()
    {
        examName = (TextView) findViewById(R.id.textViewExamName);
        examDescription = (TextView) findViewById(R.id.textViewExamDescription);
        question = (TextView) findViewById(R.id.textViewQuestion);
        answer = (EditText) findViewById(R.id.editTextAnswer);
        next = (ImageView) findViewById(R.id.imageViewNextWord);
        back = (ImageView) findViewById(R.id.imageViewBack);
    }

    private void startExam()
    {
        ExamStarter examStarter;

        try
        {
            examStarter = new ExamStarter(this, examId);
        }
        catch(Exception e)
        {
            Log.e("", "", e);
            learningAndExams();

            return;
        }

        examStarter.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    next.performClick();
                }

                @Override
                public void onFailure()
                {
                    learningAndExams();
                }
            }
        );

        examStarter.execute();
    }

    private String fetchUserAnswer()
    {
        String userAnswer = answer.getText().toString();
        answer.setText(EMPTY_STRING);

        return userAnswer;
    }
}
