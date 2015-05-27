package net.elenx.fishmash.models;

import com.fasterxml.jackson.annotation.JsonProperty;

// all fields and constructor are used by spring-android
@SuppressWarnings("unused")
public class ExamSummary extends FishmashModel
{
    private String answer;
    private boolean finished;
    private boolean passed;
    private String meaning;

    @JsonProperty("exam_finished")
    private boolean isExamFinished;
    private String phrase;

    ExamSummary()
    {
        // default constructor is used by spring-android
    }

    public String getAnswer()
    {
        return answer;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public boolean isPassed()
    {
        return passed;
    }

    public String getMeaning()
    {
        return meaning;
    }

    public boolean isExamFinished()
    {
        return isExamFinished;
    }

    public String getPhrase()
    {
        return phrase;
    }
}
