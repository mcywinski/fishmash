package net.elenx.fishmash.models;

import android.database.Cursor;

public class Authenticate extends FishmashModel
{
    private String token;
    private long user_id;
    private String created_at;
    private String updated_at;

    // default constructor is used by spring-android
    @SuppressWarnings("unused")
    Authenticate()
    {

    }

    public Authenticate(Cursor cursor)
    {
        super(cursor);
        this.token = cursor.getString(1);
        this.user_id = cursor.getLong(2);
        this.created_at = cursor.getString(3);
        this.updated_at = cursor.getString(4);
    }

    public String getToken()
    {
        return token;
    }

    public long getUser_id()
    {
        return user_id;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }
}
