package net.elenx.fishmash.models;

import android.database.Cursor;

public class Authenticate extends FishmashModel
{
    private final String token;
    private final long userId;
    private final String createdAt;
    private final String updatedAt;

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
