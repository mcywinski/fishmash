package net.elenx.fishmash.models;

public class Language
{
    private final long id;
    private String name;

    public Language(long id)
    {
        this.id = id;
    }

    public long getId()
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
