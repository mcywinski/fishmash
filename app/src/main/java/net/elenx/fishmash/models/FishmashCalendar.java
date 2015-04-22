package net.elenx.fishmash.models;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class FishmashCalendar extends GregorianCalendar
{
    @SuppressLint({"SimpleDateFormat"})
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    public FishmashCalendar(String sqlDate)
    {
        setFromSqlString(sqlDate);
    }

    private void setFromSqlString(String sqlDate)
    {
        try
        {
            setTime(dateFormat.parse(sqlDate));
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
    }

    public String getAsSqlString()
    {
        return dateFormat.format(getTime());
    }
}
