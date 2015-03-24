package net.elenx.fishmash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class FishmashOpener extends SQLiteOpenHelper
{
    private static final List<String> createTableQueries = new LinkedList<>();

    public FishmashOpener(Context context)
    {
        super(context, Constant.DATABASE_NAME, null, 1);

        createTableQueries.add(context.getString(R.string.create_wordlists));
        createTableQueries.add(context.getString(R.string.create_words));
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
