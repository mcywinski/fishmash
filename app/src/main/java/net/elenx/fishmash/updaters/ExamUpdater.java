package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.Exam;

public class ExamUpdater extends FishmashUpdater
{
    public ExamUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void download()
    {
        Log.e("before", "before");

        Exam[] exams = fishmashRest.getForObject(Constant.EXAMS, Exam[].class, buildParameters());

        Log.e("after", "after");

        for(Exam exam : exams)
        {
            Log.e("exam", String.valueOf(exam.getId()));
        }
    }
}
