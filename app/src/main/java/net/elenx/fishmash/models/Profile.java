package net.elenx.fishmash.models;

import android.database.Cursor;

public class Profile extends FishmashModel
{
    private FishmashCalendar createdAt;
    private String email;
    private String login;
    private FishmashCalendar updatedAt;
    private String userType;

    public Profile(Cursor cursor)
    {
        super(cursor);
        this.createdAt = new FishmashCalendar(cursor.getString(1));
        this.email = cursor.getString(2);
        this.login = cursor.getString(3);
        this.updatedAt = new FishmashCalendar(cursor.getString(4));
        this.userType = cursor.getString(5);
    }

    public String getCreatedAt()
    {
        return createdAt.getAsSqlString();
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
        return updatedAt.getAsSqlString();
    }

    public String getUserType()
    {
        return userType;
    }
}