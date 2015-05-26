package net.elenx.fishmash.models;

import android.database.Cursor;

public class Profile extends FishmashModel
{
    private final FishmashCalendar createdAt;
    private final String email;
    private final String login;
    private final FishmashCalendar updatedAt;
    private final String userType;

    public Profile(Cursor cursor)
    {
        super(cursor);
        createdAt = new FishmashCalendar(cursor.getString(1));
        email = cursor.getString(2);
        login = cursor.getString(3);
        updatedAt = new FishmashCalendar(cursor.getString(4));
        userType = cursor.getString(5);
    }

    public String getCreatedAt()
    {
        return createdAt.inSimpleFormat();
    }

    public String getEmail()
    {
        return email;
    }

    public String getLogin()
    {
        return login;
    }

    public String getUpdatedAt()
    {
        return updatedAt.inSimpleFormat();
    }

    public String getUserType()
    {
        return userType;
    }
}