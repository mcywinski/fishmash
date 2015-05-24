package net.elenx.fishmash.models;

import android.database.Cursor;

public class Profile extends FishmashModel
{
    private FishmashCalendar created_at;
    private String email;
    private String login;
    private FishmashCalendar updated_at;
    private String user_type;

    public Profile()
    {

    }

    public Profile(Cursor cursor)
    {
        super(cursor);
        this.created_at = new FishmashCalendar(cursor.getString(1));
        this.email = cursor.getString(2);
        this.login = cursor.getString(3);
        this.updated_at = new FishmashCalendar(cursor.getString(4));
        this.user_type = cursor.getString(5);
    }

    public String getCreated_at()
    {
        return created_at.getAsSqlString();
    }

    public String getEmail()
    {
        return email;
    }

    public String getLogin()
    {
        return login;
    }

    public String getUpdated_at()
    {
        return updated_at.getAsSqlString();
    }

    public String getUser_type()
    {
        return user_type;
    }
}