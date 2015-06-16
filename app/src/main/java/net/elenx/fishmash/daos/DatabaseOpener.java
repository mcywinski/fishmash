package net.elenx.fishmash.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.elenx.fishmash.R;
import net.elenx.fishmash.utilities.Fishmash;

import java.util.LinkedList;
import java.util.List;

class DatabaseOpener extends SQLiteOpenHelper
{
    private static final List<String> createTableQueries = new LinkedList<>();

    public DatabaseOpener(Context context)
    {
        super(context, Fishmash.DATABASE_NAME, null, 1);

        createTableQueries.add(context.getString(R.string.wordList_create));
        createTableQueries.add(context.getString(R.string.words_create));
        createTableQueries.add(context.getString(R.string.authenticate_create));
        createTableQueries.add(context.getString(R.string.profiles_create));
        createTableQueries.add(context.getString(R.string.exams_create));
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for(String createTableQuery : createTableQueries)
        {
            db.execSQL(createTableQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
