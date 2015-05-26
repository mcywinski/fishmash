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
    private final static SimpleDateFormat dominikDateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.GERMAN);
    private final static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

    private final static SimpleDateFormat[] simpleDataFormats = new SimpleDateFormat[]
    {
        sqlDateFormat,
        dominikDateFormat,
        shortDateFormat
    };

    public FishmashCalendar(String sqlDate)
    {
        boolean success = false;

        for(SimpleDateFormat simpleDateFormat : simpleDataFormats)
        {
            try
            {
                setTime(simpleDateFormat.parse(sqlDate));
                success = true;
                break;
            }
            catch(ParseException ignored)
            {
            }
        }

        if(!success)
        {
            setTimeInMillis(Long.MAX_VALUE);
            Log.e("FishmashCalendar fail-safe - setting time to max", sqlDate);
        }
    }

    public String inSqlFormat()
    {
        return sqlDateFormat.format(getTime());
    }

    public String inSimpleFormat()
    {
        return dominikDateFormat.format(getTime());
    }
}
