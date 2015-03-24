package net.elenx.fishmash.openers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.elenx.fishmash.Constant;

public class FishmashSQLiteOpener extends SQLiteOpenHelper
{
    protected static String create;

    public FishmashSQLiteOpener(Context context)
    {
        super(context, Constant.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        if(create != null)
        {
            db.execSQL(create);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
