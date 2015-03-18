package elenx.net.fishmash.models;

public class Language
{
    int id;
    String name;

    public Language(int id)
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
}
