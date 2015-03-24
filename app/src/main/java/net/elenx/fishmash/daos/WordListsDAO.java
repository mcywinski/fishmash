package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.openers.WordListsOpener;

public class WordListsDAO extends FishmashDAO<WordList>
{
    public WordListsDAO(Context context)
    {
        sqLiteDatabase = new WordListsOpener(context).getWritableDatabase();

        TABLE = "wordLists";
        COLUMNS = new String[]
        {
            "id",
            "name",
            "description",
            "mainLanguageId",
            "foreignLanguageId",
            "updatedAt",
        };
    }

    @Override
    public void insert(WordList wordList)
    {
        ContentValues contentValues = modelToContentValues(wordList);
        contentValues.put(COLUMNS[0], wordList.getId());

        sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    @Override
    public void update(WordList wordList)
    {
        ContentValues contentValues = modelToContentValues(wordList);
        String[] updateId = new String[]{String.valueOf(wordList.getId())};

        sqLiteDatabase.update(TABLE, contentValues, "id=?", updateId);
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
        contentValues.put(COLUMNS[1], wordList.getName());
        contentValues.put(COLUMNS[2], wordList.getDescription());
        contentValues.put(COLUMNS[3], wordList.getMainLanguage().getId());
        contentValues.put(COLUMNS[4], wordList.getForeignLanguage().getId());
        contentValues.put(COLUMNS[5], wordList.getUpdatedAt().getAsSqlString());

        return contentValues;
    }
}
