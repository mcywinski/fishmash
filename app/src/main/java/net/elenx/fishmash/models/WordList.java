package net.elenx.fishmash.models;

import org.json.JSONException;
import org.json.JSONObject;

public class WordList
{
    private int id;
    private String name;
    private String description;
    private Language mainLanguage;
    private Language foreignLanguage;
    private FishmashCalendar updatedAt;

    public WordList(int id)
    {
        this.id = id;
    }

    public WordList(JSONObject json) throws JSONException
    {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.mainLanguage = new Language(json.getInt("main_language_id"));
        this.foreignLanguage = new Language(json.getInt("foreign_language_id"));
        this.updatedAt = new FishmashCalendar(json.getString("updated_at"));
    }

    public int getId()
    {
        return id;
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

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Language getMainLanguage()
    {
        return mainLanguage;
    }

    public void setMainLanguage(Language mainLanguage)
    {
        this.mainLanguage = mainLanguage;
    }

    public void setMainLanguage(int mainLanguageId)
    {
        setMainLanguage(new Language(mainLanguageId));
    }

    public Language getForeignLanguage()
    {
        return foreignLanguage;
    }

    public void setForeignLanguage(Language foreignLanguage)
    {
        this.foreignLanguage = foreignLanguage;
    }

    public void setForeignLanguage(int foreignLanguageId)
    {
        setForeignLanguage(new Language(foreignLanguageId));
    }

    public FishmashCalendar getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(FishmashCalendar updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(String sqlDate)
    {
        setUpdatedAt(new FishmashCalendar(sqlDate));
    }
}
