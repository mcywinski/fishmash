package net.elenx.fishmash.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.R;

import java.util.LinkedList;
import java.util.List;

class FishmashOpener extends SQLiteOpenHelper
{
    private static final List<String> createTableQueries = new LinkedList<>();

    public FishmashOpener(Context context)
    {
        super(context, Constant.DATABASE_NAME, null, 1);

        createTableQueries.add(context.getString(R.string.create_wordLists));
        createTableQueries.add(context.getString(R.string.create_words));
        createTableQueries.add(context.getString(R.string.create_authenticate));
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
