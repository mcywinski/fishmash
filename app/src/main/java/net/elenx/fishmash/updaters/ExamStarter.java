package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.utilities.Fishmash;

import java.util.Map;

public class ExamStarter extends FishmashUpdater
{
    private Map<String, String> parameters;

    public ExamStarter(OptionsActivity optionsActivity, long examId) throws Exception
    {
        super(optionsActivity);

        parameters = buildParameters();
        parameters.put(Fishmash.EXAM_ID, String.valueOf(examId));
    }

    @Override
    void download() throws Exception
    {
        REST_INTERCEPTOR.postForObject(Fishmash.EXAMS_EXAMID_START_TOKEN, null, String.class, parameters);
    }
}
