package net.elenx.fishmash.models;

/**
{
  "id": 37,
  "answer": null,
  "finished": null,
  "passed": null,
  "meaning": "Pies",
  "exam_finished": false
}
 */

// all fields and constructor is used by spring-android
@SuppressWarnings("unused")
public class ExamQuestion extends FishmashModel
{
    private String meaning;
    private boolean exam_finished;

    ExamQuestion()
    {
        // default constructor is used by spring-android
    }

    public String getMeaning()
    {
        return meaning;
    }

    public boolean isExam_finished()
    {
        return exam_finished;
    }
}
