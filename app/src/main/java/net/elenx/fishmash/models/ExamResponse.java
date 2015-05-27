package net.elenx.fishmash.models;

// all fields and constructor is used by spring-android
@SuppressWarnings("unused")
public class ExamResponse extends FishmashModel
{
    private boolean saved;
    private String messsage; // there is a typo in API

    ExamResponse()
    {
        // default constructor is used by spring-android
    }

    public boolean isSaved()
    {
        return saved;
    }

    public String getMesssage()
    {
        return messsage;
    }
}
