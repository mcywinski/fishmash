package net.elenx.fishmash.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class FishmashCalendar extends GregorianCalendar
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    public FishmashCalendar(String sqlDate)
    {
        setFromSqlString(sqlDate);
    }

    public void setFromSqlString(String sqlDate)
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
