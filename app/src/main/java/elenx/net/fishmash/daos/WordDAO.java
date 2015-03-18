package elenx.net.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import elenx.net.fishmash.models.Word;
import elenx.net.fishmash.openers.WordsSQLiteOpener;

public class WordDAO
{
    private final static String TABLE = "words";
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
    private final WordsSQLiteOpener wordsSQLiteOpener;

    public WordDAO(Context context)
    {
        wordsSQLiteOpener = new WordsSQLiteOpener(context);
        sqLiteDatabase = wordsSQLiteOpener.getWritableDatabase();
    }

    public void close()
    {
        wordsSQLiteOpener.close();
    }

    public void insert(Word word)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[0], word.getId());
        contentValues.put(COLUMNS[1], word.getName());
        contentValues.put(COLUMNS[2], word.getDescription());
        contentValues.put(COLUMNS[3], word.getMainLanguage().getId());
        contentValues.put(COLUMNS[4], word.getForeignLanguage().getId());
        contentValues.put(COLUMNS[5], word.getCreatedAt().getAsSqlString());
        contentValues.put(COLUMNS[6], word.getUpdatedAt().getAsSqlString());

        sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    public void insert(List<Word> wordList)
    {
        for(Word word : wordList)
        {
            insert(word);
        }
    }

    public void delete(Word word)
    {
        sqLiteDatabase.delete(TABLE, "id=" + word.getId(), null);
    }

    public void truncate()
    {
        sqLiteDatabase.delete(TABLE, null, null);
    }

    public List<Word> selectAll()
    {
        List<Word> wordList = new LinkedList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE, COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Word word = cursorToWord(cursor);
            wordList.add(word);
            cursor.moveToNext();
        }

        cursor.close();

        return wordList;
    }

    public long count()
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + TABLE, null);

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    private Word cursorToWord(Cursor cursor)
    {
        Word word = new Word(cursor.getInt(0));
        word.setName(cursor.getString(1));
        word.setDescription(cursor.getString(2));
        word.setMainLanguage(cursor.getInt(3));
        word.setForeignLanguage(cursor.getInt(4));
        word.setCreatedAt(cursor.getString(5));
        word.setUpdatedAt(cursor.getString(6));

        return word;
    }
}
