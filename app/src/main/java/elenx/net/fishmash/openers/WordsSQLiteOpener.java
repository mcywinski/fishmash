package elenx.net.fishmash.openers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import elenx.net.fishmash.Constant;
import elenx.net.fishmash.R;

public class WordsSQLiteOpener extends SQLiteOpenHelper
{
    private static String create;

    public WordsSQLiteOpener(Context context)
    {
        super(context, Constant.DATABASE_NAME, null, 1);
        create = context.getString(R.string.create);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
