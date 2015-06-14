package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
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
    private TableRow tableRowExam;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_exam);

        validateExamId();
        redirectToSummaryIfFinished();
        prepareQuestionListener();
        prepareViews();
        startExam();
        prepareTimer();
    }

    private void validateExamId()
    {
        examId = getIntent().getLongExtra(Fishmash.EXAM_ID, -1);

        if(examId <= 0)
        {
            learningAndExams();
        }
    }

    private void redirectToSummaryIfFinished()
    {
        exam = new ExamDAO(this).select(examId);

        if(exam.isFinished())
        {
            switchToSummary();
        }
    }

    private void prepareQuestionListener()
    {
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
                switchToSummary();
            }
        };
    }

    private void switchToSummary()
    {
        switchIntentTo(SummaryActivity.class, Fishmash.EXAM_ID, examId);
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

        answer.setOnEditorActionListener
        (
            new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent)
                {
                    next.performClick();

                    return false;
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
                        Log.e(EMPTY_STRING, EMPTY_STRING, e);
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
        tableRowExam = (TableRow) findViewById(R.id.tableRowExam);
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
            Log.e(EMPTY_STRING, EMPTY_STRING, e);
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

    @Override
    public void onKeyboardOpenedEvent()
    {
        super.onKeyboardOpenedEvent();
        tableRowExam.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onKeyboardClosedEvent()
    {
        super.onKeyboardClosedEvent();
        tableRowExam.setVisibility(View.VISIBLE);
    }

    private void prepareTimer()
    {
        new CountDownTimer(exam.getTimeLimit() * 1000, 60 * 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                long secondsUntilFinished = (long) Math.floor(millisUntilFinished / 1000);

                Toast.makeText(me , String.format("%d seconds", secondsUntilFinished), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish()
            {
                switchIntentTo(SummaryActivity.class, Fishmash.EXAM_ID, examId);
            }
        }.start();
    }
}
