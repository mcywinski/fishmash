package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.R;
import net.elenx.fishmash.models.Exam;

public class ExamDAO extends FishmashDAO<Exam>
{
    public ExamDAO(Context context)
    {
        super(context, R.string.exams_table_name, R.array.exams_columns);
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
        contentValues.put(columns[0], exam.getId());
        contentValues.put(columns[1], exam.getName());
        contentValues.put(columns[2], exam.getDate_exam_start().inSqlFormat());
        contentValues.put(columns[3], exam.getDate_exam_finish().inSqlFormat());
        contentValues.put(columns[4], exam.getDate_practice_start().inSqlFormat());
        contentValues.put(columns[5], exam.getDate_practice_finish().inSqlFormat());
        contentValues.put(columns[6], exam.getWord_count());
        contentValues.put(columns[7], exam.getIs_finished() ? 1 : 0);

        return contentValues;
    }
}
