package elenx.net.fishmash.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class WordList
{
    private List<Word> wordList;
    private Iterator<Word> wordListIterator;

    private int id;
    private String name;
    private String description;
    private Language mainLanguage;
    private Language foreignLanguage;
    private FishmashCalendar createdAt;
    private FishmashCalendar updatedAt;

    public WordList(int id)
    {
        this.id = id;
    }

    public WordList(JSONObject jsonObject) throws JSONException
    {
        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
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
        this.mainLanguage = new Language(mainLanguageId);
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
        this.foreignLanguage = new Language(foreignLanguageId);
    }

    public FishmashCalendar getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(FishmashCalendar createdAt)
    {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(String sqlDate)
    {
        this.createdAt = new FishmashCalendar(sqlDate);
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
        this.updatedAt = new FishmashCalendar(sqlDate);
    }
}
