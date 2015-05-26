package net.elenx.fishmash.models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FishmashCalendar extends GregorianCalendar
{
    // there is no Locale.POLAND nor Locale.POLISH, and closest one is GERMAN/Y
    private final static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.GERMAN);
    private final static SimpleDateFormat standardDateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.GERMAN);
    private final static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

    private final static SimpleDateFormat[] simpleDataFormats = new SimpleDateFormat[]
    {
        sqlDateFormat,      // Michal's format from API
        standardDateFormat, // Dominik's format from tests and user experience
        shortDateFormat     // Kamil Galek's format from layouts
    };

    public FishmashCalendar(String sqlDate)
    {
        boolean failed = true;

        for(SimpleDateFormat simpleDateFormat : simpleDataFormats)
        {
            try
            {
                setTime(simpleDateFormat.parse(sqlDate));
                failed = false;
                break;
            }
            catch(ParseException ignored)
            {
            }
        }

        if(failed)
        {
            setTimeInMillis(Long.MAX_VALUE);
            Log.e("FishmashCalendar fail - setting time to max", sqlDate);
        }
    }

    public String inSqlFormat()
    {
        return sqlDateFormat.format(getTime());
    }

    public String inStandardFormat()
    {
        return standardDateFormat.format(getTime());
    }

    public String inShortFormat()
    {
        return shortDateFormat.format(getTime());
    }
}
