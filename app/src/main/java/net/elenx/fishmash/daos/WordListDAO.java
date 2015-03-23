package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.openers.WordListQLiteOpener;

import java.util.LinkedList;
import java.util.List;

public class WordListDAO
{
    private final static String TABLE = "wordLists";
    private final static String[] COLUMNS = new String[]
    {
        "id",
        "name",
        "description",
        "mainLanguageId",
        "foreignLanguageId",
        "updatedAt",
    };

    private final SQLiteDatabase sqLiteDatabase;
    private final WordListQLiteOpener wordListQLiteOpener;

    public WordListDAO(Context context)
    {
        wordListQLiteOpener = new WordListQLiteOpener(context);
        sqLiteDatabase = wordListQLiteOpener.getWritableDatabase();
    }

    public void close()
    {
        wordListQLiteOpener.close();
    }

    public void insert(List<WordList> wordLists)
    {
        for(WordList wordList : wordLists)
        {
            insert(wordList);
        }
    }

    public void insert(WordList wordList)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[0], wordList.getId());
        contentValues.put(COLUMNS[1], wordList.getName());
        contentValues.put(COLUMNS[2], wordList.getDescription());
        contentValues.put(COLUMNS[3], wordList.getMainLanguage().getId());
        contentValues.put(COLUMNS[4], wordList.getForeignLanguage().getId());
        contentValues.put(COLUMNS[5], wordList.getUpdatedAt().getAsSqlString());

        sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    public void delete(WordList wordList)
    {
        try
        {
            sqLiteDatabase.delete(TABLE, "id=" + wordList.getId(), null);
        }
        catch(SQLiteException e)
        {
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    public void truncate()
    {
        try
        {
            sqLiteDatabase.delete(TABLE, null, null);
        }
        catch(SQLiteException e)
        {
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    public List<WordList> selectAll()
    {
        List<WordList> wordLists = new LinkedList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE, COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            WordList wordList = cursorToWordList(cursor);
            wordLists.add(wordList);
            cursor.moveToNext();
        }

        cursor.close();

        return wordLists;
    }

    public long count()
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + TABLE, null);

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    private WordList cursorToWordList(Cursor cursor)
    {
        WordList wordList = new WordList(cursor.getInt(0));
        wordList.setName(cursor.getString(1));
        wordList.setDescription(cursor.getString(2));
        wordList.setMainLanguage(cursor.getInt(3));
        wordList.setForeignLanguage(cursor.getInt(4));
        wordList.setUpdatedAt(cursor.getString(5));

        return wordList;
    }
}
