package net.elenx.fishmash.models;

import android.database.Cursor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authenticate extends FishmashModel
{
    private String token;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @SuppressWarnings("unused")
    Authenticate()
    {
        // default constructor is used by spring-android
    }

    public Authenticate(Cursor cursor)
    {
        super(cursor);
        token = cursor.getString(1);
        userId = cursor.getLong(2);
        createdAt = cursor.getString(3);
        updatedAt = cursor.getString(4);
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
