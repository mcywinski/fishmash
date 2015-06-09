package net.elenx.fishmash.updaters.listeners;

public interface ExamQuestionListener
{
    void prepareQuestion(String nextQuestion);
    void examFinished();
}
