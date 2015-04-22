package net.elenx.fishmash.models;

import android.database.Cursor;

public class Profile extends FishmashModel
{
    private final String created_at;
    private final String email;
    private final String login;
    private final String updated_at;
    private final String user_type;

    public Profile(Cursor cursor)
    {
        super(cursor);
        this.created_at = cursor.getString(1);
        this.email = cursor.getString(2);
        this.login = cursor.getString(3);
        this.updated_at = cursor.getString(4);
        this.user_type = cursor.getString(5);
    }

    public String getCreated_at()
    {
        return created_at;
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
        return updated_at;
    }

    public String getUser_type()
    {
        return user_type;
    }
}