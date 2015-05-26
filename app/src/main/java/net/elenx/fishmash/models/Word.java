package net.elenx.fishmash.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

public class Word extends FishmashModel
{
    private String phrase;
    private String meaning;

    public Word(Cursor cursor)
    {
        super(cursor);
        phrase = cursor.getString(1);
        meaning = cursor.getString(2);
    }

    public Word(JSONObject json) throws JSONException
    {
        super(json);
        phrase = json.getString("phrase");
        meaning = json.getString("meaning");
    }

    public String getPhrase()
    {
        return phrase;
    }

    public String getMeaning()
    {
        return meaning;
    }
}
