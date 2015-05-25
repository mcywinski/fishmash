package net.elenx.fishmash.models;

import org.json.JSONException;
import org.json.JSONObject;

/*
{
    "id": 1,
    "name": "Test 1 - Animals",
    "date_exam_start": "2016-01-09",
    "date_exam_finish": "2016-01-16",
    "date_practice_start": "2016-01-01",
    "date_practice_finish": "2016-01-08",
    "word_count": 4,
    "is_finished": true
},
*/

public class Exam extends FishmashModel
{
    private String name;
    private FishmashCalendar dateExamStart;
    private FishmashCalendar dateExamFinish;
    private FishmashCalendar datePracticeStart;
    private FishmashCalendar datePracticeFinish;
    private int wordCount;
    private boolean isFinished;

    Exam(JSONObject json) throws JSONException
    {
        super(json);
    }
}
