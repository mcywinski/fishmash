package net.elenx.fishmash.openers;

import android.content.Context;

import net.elenx.fishmash.R;

public class WordListsOpener extends FishmashSQLiteOpener
{
    public WordListsOpener(Context context)
    {
        super(context);
        create = context.getString(R.string.create_wordlists);
    }
}
