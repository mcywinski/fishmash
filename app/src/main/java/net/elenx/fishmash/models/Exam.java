package net.elenx.fishmash.models;

import android.database.Cursor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.elenx.fishmash.models.adapters.FishmashCalendar;

@JsonIgnoreProperties("is_started")
public class Exam extends FishmashModel
{
    @JsonProperty("name") private String name;
    @JsonProperty("date_exam_start") private FishmashCalendar dateExamStart;
    @JsonProperty("date_exam_finish") private FishmashCalendar dateExamFinish;
    @JsonProperty("date_practice_start") private FishmashCalendar datePracticeStart;
    @JsonProperty("date_practice_finish") private FishmashCalendar datePracticeFinish;
    @JsonProperty("word_count") private int wordCount;
    @JsonProperty("is_finished") private boolean isFinished;
    @JsonProperty("time_limit") private long timeLimit;
    @JsonProperty("is_started") private boolean isStarted;

    @SuppressWarnings("unused")
    Exam()
    {
        // default constructor is used by spring-android
    }

    public Exam(Cursor cursor)
    {
        super(cursor);
        name = cursor.getString(1);
        dateExamStart = new FishmashCalendar(cursor.getString(2));
        dateExamFinish = new FishmashCalendar(cursor.getString(3));
        datePracticeStart = new FishmashCalendar(cursor.getString(4));
        datePracticeFinish = new FishmashCalendar(cursor.getString(5));
        wordCount = cursor.getInt(6);
        isFinished = cursor.getInt(7) == 1;
        timeLimit = cursor.getLong(8);
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

    public long getTimeLimit()
    {
        return timeLimit;
    }
}
