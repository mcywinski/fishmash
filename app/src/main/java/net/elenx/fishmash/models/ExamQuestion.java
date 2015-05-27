package net.elenx.fishmash.models;

import com.fasterxml.jackson.annotation.JsonProperty;

// all fields and constructor is used by spring-android
@SuppressWarnings("unused")
public class ExamQuestion extends FishmashModel
{
    private String meaning;

    @JsonProperty("exam_finished")
    private boolean examFinished;

    ExamQuestion()
    {
        // default constructor is used by spring-android
    }

    public String getMeaning()
    {
        return meaning;
    }

    public boolean isExamFinished()
    {
        return examFinished;
    }
}
