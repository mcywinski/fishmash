package elenx.net.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import elenx.net.fishmash.models.WordList;
import elenx.net.fishmash.openers.WordListQLiteOpener;

public class WordListDAO
{
    private final static String TABLE = "wordList";
    private final static String[] COLUMNS = new String[]
    {
        "id",
        "name",
        "description",
        "mainLanguage",
        "foreignLanguage",
        "createdAt",
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

    public void insert(WordList wordList)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[0], wordList.getId());
        contentValues.put(COLUMNS[1], wordList.getName());
        contentValues.put(COLUMNS[2], wordList.getDescription());
        contentValues.put(COLUMNS[3], wordList.getMainLanguage().getId());
        contentValues.put(COLUMNS[4], wordList.getForeignLanguage().getId());
        contentValues.put(COLUMNS[5], wordList.getCreatedAt().getAsSqlString());
        contentValues.put(COLUMNS[6], wordList.getUpdatedAt().getAsSqlString());

        sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    public void insert(List<WordList> wordLists)
    {
        for(WordList wordList : wordLists)
        {
            insert(wordList);
        }
    }

    public void delete(WordList wordList)
    {
        sqLiteDatabase.delete(TABLE, "id=" + wordList.getId(), null);
    }

    public void truncate()
    {
        sqLiteDatabase.delete(TABLE, null, null);
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
        wordList.setCreatedAt(cursor.getString(5));
        wordList.setUpdatedAt(cursor.getString(6));

        return wordList;
    }
}
