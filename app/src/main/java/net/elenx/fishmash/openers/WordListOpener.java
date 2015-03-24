package net.elenx.fishmash.openers;

import android.content.Context;

import elenx.net.fishmash.R;

public class WordListOpener extends FishmashSQLiteOpener
{
    public WordListOpener(Context context)
    {
        super(context);
        create = context.getString(R.string.create_wordlists);
    }
}
