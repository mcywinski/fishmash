package net.elenx.fishmash.models;

import com.fasterxml.jackson.annotation.JsonProperty;

// all fields and constructor is used by spring-android
@SuppressWarnings("unused")
public class ExamResponse extends FishmashModel
{
    private boolean saved;

    @JsonProperty("messsage") // there is a typo in API
    private String message;

    ExamResponse()
    {
        // default constructor is used by spring-android
    }

    public boolean isSaved()
    {
        return saved;
    }

    public String getMessage()
    {
        return message;
    }
}
