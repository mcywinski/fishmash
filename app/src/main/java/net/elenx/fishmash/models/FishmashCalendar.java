package net.elenx.fishmash.models;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class FishmashCalendar extends GregorianCalendar
{
    @SuppressLint({"SimpleDateFormat"})
    private final static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    @SuppressLint({"SimpleDateFormat"})
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");

    public FishmashCalendar(String sqlDate)
    {
        setFromSqlString(sqlDate);
    }

    private void setFromSqlString(String sqlDate)
    {
        try
        {
            setTime(sqlDateFormat.parse(sqlDate));
        }
        catch(ParseException ignored)
        {
        }
    }

    private void setFromSimpleString(String sqlDate)
    {
        try
        {
            setTime(simpleDateFormat.parse(sqlDate));
        }
        catch(ParseException ignored)
        {
        }
    }

    public String getAsSqlString()
    {
        return sqlDateFormat.format(getTime());
    }

    public String getAsSimpleString()
    {
        return simpleDateFormat.format(getTime());
    }
}
