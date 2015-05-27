package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.elenx.fishmash.R;
import net.elenx.fishmash.models.Profile;

public class ProfileDAO extends FishmashDAO<Profile>
{
    public ProfileDAO(Context context)
    {
        super(context, R.string.profiles_table_name, R.array.profiles_columns);
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
        contentValues.put(columns[1], profile.getCreated_at());
        contentValues.put(columns[2], profile.getEmail());
        contentValues.put(columns[3], profile.getLogin());
        contentValues.put(columns[4], profile.getUpdated_at());
        contentValues.put(columns[5], profile.getUser_type());

        return contentValues;
    }
}
