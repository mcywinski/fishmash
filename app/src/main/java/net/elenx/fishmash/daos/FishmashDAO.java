package net.elenx.fishmash.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import net.elenx.fishmash.models.ModelInterface;

import java.util.LinkedList;
import java.util.List;

public abstract class FishmashDAO<FishmashModel> implements DAOInterface<FishmashModel>
{
    protected static String TABLE = "";
    protected static String[] COLUMNS = new String[]{};

    protected SQLiteDatabase sqLiteDatabase;

    @Override
    public List<FishmashModel> selectAll()
    {
        List<FishmashModel> fishmashModelList = new LinkedList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE, COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            FishmashModel fishmashModel = cursorToModel(cursor);
            fishmashModelList.add(fishmashModel);
            cursor.moveToNext();
        }

        cursor.close();

        return fishmashModelList;
    }

    @Override
    public FishmashModel select(long id)
    {
        String[] idToSelect = new String[]{String.valueOf(id)};

        Cursor cursor = sqLiteDatabase.query(TABLE, COLUMNS, "id = ?", idToSelect, null, null, null);

        cursor.moveToFirst();

        return cursorToModel(cursor);
    }

    @Override
    public void insert(List<FishmashModel> fishmashModelList)
    {
        for(FishmashModel fishmashModel : fishmashModelList)
        {
            insert(fishmashModel);
        }
    }

    @Override
    public void update(List<FishmashModel> fishmashModelList)
    {
        for(FishmashModel fishmashModel : fishmashModelList)
        {
            update(fishmashModel);
        }
    }

    @Override
    public void delete(long id)
    {
        try
        {
            sqLiteDatabase.delete(TABLE, "id=" + id, null);
        }
        catch(SQLiteException e)
        {
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    @Override
    public void delete(FishmashModel fishmashModel)
    {
        ModelInterface modelInterface = (ModelInterface) fishmashModel;

        delete(modelInterface.getId());
    }

    @Override
    public long count()
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + TABLE, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    @Override
    public void truncate()
    {
        try
        {
            sqLiteDatabase.delete(TABLE, null, null);
        }
        catch(SQLiteException e)
        {
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    @Override
    public void close()
    {
        sqLiteDatabase.close();
    }
}
