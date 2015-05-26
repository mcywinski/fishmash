package net.elenx.fishmash.updaters;

public interface ExamQuestionListener
{
    void prepareQuestion(String nextQuestion);
    void examFinished();
}
