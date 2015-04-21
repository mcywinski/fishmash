package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.User;

public class UserDAO extends FishmashDAO<User>
{
    public UserDAO(Context context)
    {
        super
        (
            context,
            "wordLists",
            new String[]
            {
                "id",
                "name",
                "description",
                "mainLanguageId",
                "foreignLanguageId",
                "updatedAt"
            }
        );
    }

    @Override
    public User cursorToModel(Cursor cursor)
    {
        return null;
    }

    @Override
    public ContentValues modelToContentValues(User user)
    {
        return null;
    }

    @Override
    public long getIdOf(User user)
    {
        return 1;
    }
}
