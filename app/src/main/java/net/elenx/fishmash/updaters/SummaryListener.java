package net.elenx.fishmash.updaters;

import net.elenx.fishmash.models.ExamSummary;

public interface SummaryListener
{
    void showSummary(ExamSummary[] examSummaries);
}
