package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.R;
import net.elenx.fishmash.models.WordList;

public class WordListDAO extends FishmashDAO<WordList>
{
    public WordListDAO(Context context)
    {
        super(context, R.string.wordList_table_name, R.array.wordList_columns);
    }

    @Override
    public WordList cursorToModel(Cursor cursor)
    {
        return new WordList(cursor);
    }

    @Override
    public ContentValues modelToContentValues(WordList wordList)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columns[1], wordList.getName());
        contentValues.put(columns[2], wordList.getDescription());
        contentValues.put(columns[3], wordList.getMainLanguage().getId());
        contentValues.put(columns[4], wordList.getForeignLanguage().getId());
        contentValues.put(columns[5], wordList.getUpdatedAt().inSqlFormat());

        return contentValues;
    }

}
