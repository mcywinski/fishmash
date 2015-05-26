package net.elenx.fishmash.models;

import android.database.Cursor;

public class Exam extends FishmashModel
{
    private String name;
    private FishmashCalendar date_exam_start;
    private FishmashCalendar date_exam_finish;
    private FishmashCalendar date_practice_start;
    private FishmashCalendar date_practice_finish;
    private int word_count;
    private boolean is_finished;

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

    public FishmashCalendar getDate_exam_start()
    {
        return date_exam_start;
    }

    public FishmashCalendar getDate_exam_finish()
    {
        return date_exam_finish;
    }

    public FishmashCalendar getDate_practice_start()
    {
        return date_practice_start;
    }

    public FishmashCalendar getDate_practice_finish()
    {
        return date_practice_finish;
    }

    public int getWord_count()
    {
        return word_count;
    }

    public boolean getIs_finished()
    {
        return is_finished;
    }
}
