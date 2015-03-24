package net.elenx.fishmash.openers;

import android.content.Context;

import elenx.net.fishmash.R;

public class WordsOpener extends FishmashSQLiteOpener
{
    public WordsOpener(Context context)
    {
        super(context);
        create = context.getString(R.string.create_words);
    }
}
