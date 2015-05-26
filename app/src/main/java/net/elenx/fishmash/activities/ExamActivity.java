package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.updaters.Examiner;
import net.elenx.fishmash.updaters.UpdaterListener;

public class ExamActivity extends OptionsActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.learning);

        long examId = getIntent().getLongExtra("examId", -1);

        Log.e("examId", String.valueOf(examId));

        if(examId <= 0)
        {
            Toast.makeText(this, "This exam has finished or have never existed", Toast.LENGTH_LONG).show();

            return;
        }

        final Examiner examiner = new Examiner(this, examId);
        examiner.setUpdaterListener
        (
            new UpdaterListener()
            {
                @Override
                public void onSuccess()
                {
                    Log.e("success", examiner.getExamQuestion().getMeaning());
                }

                @Override
                public void onFailure()
                {
                    Log.e("fail", examiner.getExamQuestion().getMeaning());
                }
            }
        );

        examiner.execute();
    }
}
