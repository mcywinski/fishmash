package net.elenx.fishmash.models;

import android.database.Cursor;

import net.elenx.fishmash.models.secondary.Language;

import org.json.JSONException;
import org.json.JSONObject;

public class WordList extends FishmashModel
{
    private String name;
    private String description;
    private Language mainLanguage;
    private Language foreignLanguage;
    private FishmashCalendar updatedAt;

    public WordList(Cursor cursor)
    {
        super(cursor);
        name = cursor.getString(1);
        description = cursor.getString(2);
        mainLanguage = new Language(cursor.getInt(3));
        foreignLanguage = new Language(cursor.getInt(4));
        updatedAt = new FishmashCalendar(cursor.getString(5));
    }

    public WordList(JSONObject json) throws JSONException
    {
        super(json);
        name = json.getString("name");
        description = json.getString("description");
        mainLanguage = new Language(json.getInt("main_language_id"));
        foreignLanguage = new Language(json.getInt("foreign_language_id"));
        updatedAt = new FishmashCalendar(json.getString("updated_at"));
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Language getMainLanguage()
    {
        return mainLanguage;
    }

    public Language getForeignLanguage()
    {
        return foreignLanguage;
    }

    public FishmashCalendar getUpdatedAt()
    {
        return updatedAt;
    }
}
