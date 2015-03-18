package elenx.net.fishmash.models;

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
public class Word
{
    private int id;
    private String name;
    private String description;
    private Language mainLanguage;
    private Language foreignLanguage;
    private FishmashCalendar createdAt;
    private FishmashCalendar updatedAt;

    public Word(int id)
    {
        this.id = id;
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
