package net.elenx.fishmash.models;

import android.database.Cursor;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.elenx.fishmash.models.adapters.FishmashCalendar;

public class Profile extends FishmashModel
{
    @JsonProperty("created_at")
    private FishmashCalendar createdAt;

    private String email;
    private String login;

    @JsonProperty("updated_at")
    private FishmashCalendar updatedAt;

    @JsonProperty("user_type")
    private String userType;

    @SuppressWarnings("unused")
    Profile()
    {
        // default constructor is used by spring-android
    }

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
        return createdAt.inStandardFormat();
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
        return updatedAt.inStandardFormat();
    }

    public String getUserType()
    {
        return userType;
    }
}