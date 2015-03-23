package elenx.net.fishmash.models;

import org.json.JSONException;
import org.json.JSONObject;

/*
http://localhost:3000/api/lists/2
{
	"id":2,
	"name":"Animals",
	"description":"Basic animal-related vocabulary",
	"main_language_id":1,
	"foreign_language_id":2,
	"created_at":"2015-03-14T20:42:32.679Z",
	"updated_at":"2015-03-14T20:42:32.679Z"
}
 */
public class WordList
{
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
