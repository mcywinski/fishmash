package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.models.Authenticate;

public class AuthenticateDAO extends FishmashDAO<Authenticate>
{
    public AuthenticateDAO(Context context)
    {
        super
        (
            context,
            "authenticate",
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
    public Authenticate cursorToModel(Cursor cursor)
    {
        return null;
    }

    @Override
    public ContentValues modelToContentValues(Authenticate authenticate)
    {
        return null;
    }

    @Override
    public long getIdOf(Authenticate authenticate)
    {
        return 1;
    }
}
