package net.elenx.fishmash.models.adapters;

import java.util.Locale;

public class Language
{
    private final long id;
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
            case 3:
                locale = Locale.GERMANY;
                break;
            case 4:
                locale =  Locale.FRENCH;
                break;
            default:
                locale = Locale.US;
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
}
