package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.Word;
import net.elenx.fishmash.openers.WordsOpener;

public class WordsDAO extends FishmashDAO<Word>
{
    public WordsDAO(Context context)
    {
        super
        (
            "words",
            new String[]
            {
                "id",
                "phrase",
                "meaning"
            }
        );

        sqLiteDatabase = new WordsOpener(context).getWritableDatabase();
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

    @Override
    public long getId(Word word)
    {
        return word.getId();
    }
}
