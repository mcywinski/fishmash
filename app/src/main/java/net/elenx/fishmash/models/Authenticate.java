package net.elenx.fishmash.models;

import android.content.ContentValues;
import android.database.Cursor;

public class Authenticate extends FishmashModel
{
    private String token;
    private long userId;
    private String createdAt;
    private String updatedAt;

    public Authenticate(Cursor cursor)
    {
        super(cursor);
        this.token = cursor.getString(1);
        this.userId = cursor.getLong(2);
        this.createdAt = cursor.getString(3);
        this.updatedAt = cursor.getString(4);
    }

    public String getToken()
    {
        return token;
    }

    public long getUserId()
    {
        return userId;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public String getUpdatedAt()
    {
        return updatedAt;
    }
}
