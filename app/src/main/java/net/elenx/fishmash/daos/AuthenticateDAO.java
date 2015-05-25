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
                "token",
                "user_id",
                "created_at",
                "updated_at"
            }
        );
    }

    @Override
    public Authenticate cursorToModel(Cursor cursor)
    {
        return new Authenticate(cursor);
    }

    @Override
    public ContentValues modelToContentValues(Authenticate authenticate)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columns[1], authenticate.getToken());
        contentValues.put(columns[2], authenticate.getUserId());
        contentValues.put(columns[3], authenticate.getCreatedAt());
        contentValues.put(columns[4], authenticate.getUpdatedAt());

        return contentValues;
    }
}
