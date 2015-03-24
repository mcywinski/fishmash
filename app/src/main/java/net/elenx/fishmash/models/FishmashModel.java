package net.elenx.fishmash.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class FishmashModel implements ModelInterface
{
    private long id;

    FishmashModel(Cursor cursor)
    {
        this.id = cursor.getLong(0);
    }

    FishmashModel(JSONObject json) throws JSONException
    {
        this.id = json.getLong("id");
    }

    @Override
    public final long getId()
    {
        return this.id;
    }
}
