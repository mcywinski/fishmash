package net.elenx.fishmash.updaters;

import net.elenx.fishmash.utilities.Fishmash;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamSummary;
import net.elenx.fishmash.updaters.listeners.SummaryListener;

import java.util.Map;

public class SummaryUpdater extends FishmashUpdater
{
    private final long examId;
    private SummaryListener summaryListener;
    private ExamSummary[] examSummaries;

    public SummaryUpdater(OptionsActivity optionsActivity, long examId)
    {
        super(optionsActivity);
        this.examId = examId;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        if(summaryListener != null)
        {
            summaryListener.showSummary(examSummaries);
        }
    }

    @Override
    void download() throws Exception
    {
        Map<String, String> parameters = buildParameters();
        parameters.put(Fishmash.EXAM_ID, String.valueOf(examId));

        examSummaries = REST_INTERCEPTOR.getForObject(Fishmash.SUMMARY_EXAMID_TOKEN, ExamSummary[].class, parameters);
    }

    public void setSummaryListener(SummaryListener summaryListener)
    {
        this.summaryListener = summaryListener;
    }
}
