package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamSummary;
import net.elenx.fishmash.updaters.listeners.SummaryListener;

import java.util.Map;

public class SummaryUpdater extends FishmashUpdater
{
    private final long examId;
    private SummaryListener summaryListener;

    public SummaryUpdater(OptionsActivity optionsActivity, long examId)
    {
        super(optionsActivity);
        this.examId = examId;
    }

    @Override
    void download() throws Exception
    {
        Map<String, String> parameters = buildParameters();
        parameters.put("exam_id", String.valueOf(examId));

        ExamSummary[] examSummaries = fishmashRest.getForObject(Constant.SUMMARY_EXAMID_TOKEN, ExamSummary[].class, parameters);

        if(summaryListener != null)
        {
            summaryListener.showSummary(examSummaries);
        }
    }

    public void setSummaryListener(SummaryListener summaryListener)
    {
        this.summaryListener = summaryListener;
    }
}
