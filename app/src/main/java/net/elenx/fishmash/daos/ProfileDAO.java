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
        return null;
    }

    @Override
    public ContentValues modelToContentValues(Profile profile)
    {
        return null;
    }

    @Override
    public long getIdOf(Profile profile)
    {
        return 1;
    }
}
