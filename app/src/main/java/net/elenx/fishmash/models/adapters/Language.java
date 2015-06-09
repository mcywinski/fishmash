package net.elenx.fishmash.models.adapters;

import java.util.Locale;

public class Language
{
    private final long id;
    private String name;
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
                locale = null; // android cant speak Polish
                break;
            case 2:
                name = "English";
                locale = Locale.ENGLISH;
                break;
            case 3:
                name = "Deutsch";
                locale = Locale.GERMANY;
                break;
            case 4:
                name = "Francais";
                locale =  Locale.FRENCH;
                break;
            default:
                name = "UNKNOWN LANGUAGE";
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
}
