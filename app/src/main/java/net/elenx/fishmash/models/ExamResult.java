package net.elenx.fishmash.models;

/**
{
    "id": 37,
    "answer": "pies",
    "finished": true,
    "passed": false,
    "meaning": "Pies",
    "exam_finished": true,
    "phrase": "Dog"
}
**/
public class ExamResult extends FishmashModel
{
    private String answer;
    private boolean finished;
    private boolean passed;
    private String meaning;
    private boolean exam_finished;
    private String phrase;

    @SuppressWarnings("unused")
    ExamResult()
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

    public boolean isExam_finished()
    {
        return exam_finished;
    }

    public String getPhrase()
    {
        return phrase;
    }
}
