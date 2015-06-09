package net.elenx.fishmash.models.adapters;

import java.util.Locale;

public class Language
{
    private final long id;
    private String name;
    private String description;
    private Locale locale;

    public Language(long id)
    {
        this.id = id;

        concludeLocate();
    }

    private void concludeLocate()
    {
        switch((int) id)
        {
            case 1:
                name = "Polski";
                description = "Język polski";
                locale = null; // android cant speak Polish
            case 2:
                name = "English";
                description = "English language";
                locale = Locale.ENGLISH;
                break;
            case 3:
                name = "Deutsch";
                description = "Deutsch sprache";
                locale = Locale.GERMANY;
                break;
            case 4:
                name = "Francais";
                description = "Francais";
                locale =  Locale.FRENCH;
                break;
            default:
                name = "";
                description = "";
                locale = null;
        }
    }

    public long getId()
    {
        return id;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
