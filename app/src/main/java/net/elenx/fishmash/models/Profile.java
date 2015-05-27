package net.elenx.fishmash.models;

import android.database.Cursor;

import net.elenx.fishmash.models.adapters.FishmashCalendar;

public class Profile extends FishmashModel
{
    private FishmashCalendar created_at;
    private String email;
    private String login;
    private FishmashCalendar updated_at;
    private String user_type;

    @SuppressWarnings("unused")
    Profile()
    {
        // default constructor is used by spring-android
    }

    public Profile(Cursor cursor)
    {
        super(cursor);
        created_at = new FishmashCalendar(cursor.getString(1));
        email = cursor.getString(2);
        login = cursor.getString(3);
        updated_at = new FishmashCalendar(cursor.getString(4));
        user_type = cursor.getString(5);
    }

    public String getCreated_at()
    {
        return created_at.inStandardFormat();
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
        return updated_at.inStandardFormat();
    }

    public String getUser_type()
    {
        return user_type;
    }
}