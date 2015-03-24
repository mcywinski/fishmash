package net.elenx.fishmash.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Word
{
    private int id;
    private String phase;
    private String meaning;

    public Word(JSONObject jsonWord) throws JSONException
    {
        this.id = jsonWord.getInt("id");
        this.phase = jsonWord.getString("phase");
        this.meaning = jsonWord.getString("meaning");
    }
}
