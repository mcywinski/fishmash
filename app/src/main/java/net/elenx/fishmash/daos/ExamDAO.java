package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.Exam;

public class ExamDAO extends FishmashDAO<Exam>
{
    public ExamDAO(Context context)
    {
        super
        (
            context,
            "exams",
            new String[]
            {
                "id",
                "name",
                "date_exam_start",
                "date_exam_finish",
                "date_practice_start",
                "date_practice_finish",
                "word_count",
                "is_finished"
            }
        );
    }

    @Override
    protected Exam cursorToModel(Cursor cursor)
    {
        return new Exam(cursor);
    }

    @Override
    protected ContentValues modelToContentValues(Exam exam)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columns[1], exam.getId());
        contentValues.put(columns[2], exam.getName());
        contentValues.put(columns[3], exam.getDateExamStart().getAsSimpleString());
        contentValues.put(columns[4], exam.getDateExamFinish().getAsSimpleString());
        contentValues.put(columns[5], exam.getDatePracticeStart().getAsSimpleString());
        contentValues.put(columns[6], exam.getDatePracticeFinish().getAsSimpleString());
        contentValues.put(columns[7], exam.getWordCount());
        contentValues.put(columns[7], exam.isFinished() ? 1 : 0);

        return contentValues;
    }
}
