package net.elenx.fishmash.updaters;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Exam;

import java.util.Arrays;
import java.util.List;

public class ExamUpdater extends FishmashUpdater
{
    private List<Exam> examList;

    public ExamUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void download() throws Exception
    {
        Exam[] exams = REST_INTERCEPTOR.getForObject(Fishmash.EXAMS_TOKEN, Exam[].class, buildParameters());

        examList = Arrays.asList(exams);
    }

    @Override
    protected void save() throws Exception
    {
        if(examList == null)
        {
            throw new Exception("there is no exam list");
        }

        ExamDAO examDAO = new ExamDAO(optionsActivity);
        examDAO.truncate();
        examDAO.insert(examList);
    }
}
