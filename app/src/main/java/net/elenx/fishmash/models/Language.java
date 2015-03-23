package net.elenx.fishmash.models;

public class Language
{
    private int id;
    private String name;

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
