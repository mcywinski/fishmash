package net.elenx.fishmash.models;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class FishmashCalendar extends GregorianCalendar
{
    @SuppressLint({"SimpleDateFormat"})
    private final static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    @SuppressLint({"SimpleDateFormat"})
    private final static SimpleDateFormat outputDateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");

    public FishmashCalendar(String sqlDate)
    {
        setFromSqlString(sqlDate);
    }

    private void setFromSqlString(String sqlDate)
    {
        try
        {
            setTime(inputDateFormat.parse(sqlDate));
        }
        catch(ParseException ignored)
        {
        }
    }

    public String getAsSqlString()
    {
        return outputDateFormat.format(getTime());
    }
}
