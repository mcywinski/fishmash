package net.elenx.fishmash.updaters;

public interface ExaminerListener
{
    void prepareQuestion(String nextQuestion);
    void examFinished();
}
