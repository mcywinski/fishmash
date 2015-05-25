package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.Profile;

public class ProfileDAO extends FishmashDAO<Profile>
{
    public ProfileDAO(Context context)
    {
        super
        (
            context,
            "profile",
            new String[]
            {
                "id",
                "created_at",
                "email",
                "login",
                "updated_at",
                "user_type"
            }
        );
    }

    @Override
    public Profile cursorToModel(Cursor cursor)
    {
        return new Profile(cursor);
    }

    @Override
    public ContentValues modelToContentValues(Profile profile)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columns[1], profile.getCreatedAt());
        contentValues.put(columns[2], profile.getEmail());
        contentValues.put(columns[3], profile.getLogin());
        contentValues.put(columns[4], profile.getUpdatedAt());
        contentValues.put(columns[5], profile.getUserType());

        return contentValues;
    }

    @Override
    public long getIdOf(Profile profile)
    {
        return 1;
    }
}
