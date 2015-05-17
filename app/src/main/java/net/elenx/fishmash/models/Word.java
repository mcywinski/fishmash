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
        this.phrase = cursor.getString(1);
        this.meaning = cursor.getString(2);
    }

    public Word(JSONObject json) throws JSONException
    {
        super(json);
        this.phrase = json.getString("phrase");
        this.meaning = json.getString("meaning");
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
