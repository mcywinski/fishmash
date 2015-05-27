package net.elenx.fishmash.updaters.listeners;

public interface ExaminerListener
{
    void prepareQuestion(String nextQuestion);
    void examFinished();
}
