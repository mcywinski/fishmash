package net.elenx.fishmash.models;

import android.database.Cursor;

public class Exam extends FishmashModel
{
    private String name;
    private FishmashCalendar dateExamStart;
    private FishmashCalendar dateExamFinish;
    private FishmashCalendar datePracticeStart;
    private FishmashCalendar datePracticeFinish;
    private int wordCount;
    private boolean isFinished;

    // default constructor is used by spring-android
    @SuppressWarnings("unused")
    Exam()
    {

    }

    public Exam(Cursor cursor)
    {
        super(cursor);
    }

    public String getName()
    {
        return name;
    }

    public FishmashCalendar getDateExamStart()
    {
        return dateExamStart;
    }

    public FishmashCalendar getDateExamFinish()
    {
        return dateExamFinish;
    }

    public FishmashCalendar getDatePracticeStart()
    {
        return datePracticeStart;
    }

    public FishmashCalendar getDatePracticeFinish()
    {
        return datePracticeFinish;
    }

    public int getWordCount()
    {
        return wordCount;
    }

    public boolean isFinished()
    {
        return isFinished;
    }
}
