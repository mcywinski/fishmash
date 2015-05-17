package net.elenx.fishmash.models;

import java.util.Locale;

public class Language
{
    private final long id;
    private String name;

    private Locale locale;

    public Language(long id)
    {
        this.id = id;

        makeLocale();
    }

    private void makeLocale()
    {
        switch((int) id)
        {
            case 2:
                locale = Locale.US;
                break;
            case 3:
                locale = Locale.GERMANY;
                break;
            case 4:
                locale =  Locale.FRENCH;
                break;
            default:
                locale = null;
        }
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

    public Locale getLocale()
    {
        return locale;
    }
}
