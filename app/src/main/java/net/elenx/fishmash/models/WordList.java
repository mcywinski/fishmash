package net.elenx.fishmash.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WordList extends FishmashModel
{
    private String name;
    private String description;
    private Language mainLanguage;
    private Language foreignLanguage;
    private FishmashCalendar updatedAt;

    private List<Word> words;

    public WordList(Cursor cursor)
    {
        super(cursor);
        this.name = cursor.getString(1);
        this.description = cursor.getString(2);
        this.mainLanguage = new Language(cursor.getInt(3));
        this.foreignLanguage = new Language(cursor.getInt(4));
        this.updatedAt = new FishmashCalendar(cursor.getString(5));
    }

    public WordList(JSONObject json) throws JSONException
    {
        super(json);
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.mainLanguage = new Language(json.getInt("main_language_id"));
        this.foreignLanguage = new Language(json.getInt("foreign_language_id"));
        this.updatedAt = new FishmashCalendar(json.getString("updated_at"));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
