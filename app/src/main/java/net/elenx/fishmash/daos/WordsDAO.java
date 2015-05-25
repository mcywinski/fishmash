package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.R;
import net.elenx.fishmash.models.Word;

public class WordsDAO extends FishmashDAO<Word>
{
    public WordsDAO(Context context)
    {
        super(context, R.string.words_table_name, R.array.words_columns);
    }

    @Override
    public Word cursorToModel(Cursor cursor)
    {
        return new Word(cursor);
    }

    @Override
    public ContentValues modelToContentValues(Word word)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columns[1], word.getPhrase());
        contentValues.put(columns[2], word.getMeaning());

        return contentValues;
    }
}
