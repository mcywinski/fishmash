package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.WordList;

public class WordListsDAO extends FishmashDAO<WordList>
{
    public WordListsDAO(Context context)
    {
        super
        (
            context,
            "wordLists",
            new String[]
            {
                "id",
                "name",
                "description",
                "mainLanguageId",
                "foreignLanguageId",
                "updatedAt"
            }
        );
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
        contentValues.put(columns[5], wordList.getUpdatedAt().getAsSqlString());

        return contentValues;
    }

}
