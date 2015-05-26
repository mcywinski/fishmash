package net.elenx.fishmash.models;

public class ExamResponse extends FishmashModel
{
    private boolean saved;
    private String messsage;

    @SuppressWarnings("unused")
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
