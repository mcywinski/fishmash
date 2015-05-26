package net.elenx.fishmash;

import net.elenx.fishmash.models.Exam;
import net.elenx.fishmash.models.ExamAnswer;
import net.elenx.fishmash.models.ExamQuestion;

public class Examiner
{
    private long examId;

    public Examiner(Exam exam)
    {
        this(exam.getId());
    }

    public Examiner(long examId)
    {
        this.examId = examId;
    }

    public ExamQuestion nextQuestion()
    {
        return null;
    }

    public boolean sendAnswer(String answer)
    {
        ExamAnswer examAnswer = new ExamAnswer(examId, answer);

        return true;
    }
}
