package net.elenx.fishmash.models;

import android.database.Cursor;

public abstract class FishmashModel implements ModelInterface
{
    protected long id;

    public FishmashModel(Cursor cursor)
    {

    }

    @Override
    public long getId()
    {
        return this.id;
    }
}
