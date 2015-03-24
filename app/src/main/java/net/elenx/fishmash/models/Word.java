package net.elenx.fishmash.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

public class Word extends FishmashModel
{
    private String phase;
    private String meaning;

    public Word(Cursor cursor)
    {
        super(cursor);
        this.phase = cursor.getString(1);
        this.meaning = cursor.getString(2);
    }

    public Word(JSONObject json) throws JSONException
    {
        super(json);
        this.phase = json.getString("phase");
        this.meaning = json.getString("meaning");
    }

    public String getPhase()
    {
        return phase;
    }

    public String getMeaning()
    {
        return meaning;
    }
}
