package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import net.elenx.fishmash.models.FishmashModel;

import java.util.LinkedList;
import java.util.List;

public abstract class FishmashDAO<Model extends FishmashModel>
{
    private final String table;
    final String[] columns;

    private final SQLiteDatabase sqLiteDatabase;

    protected abstract Model cursorToModel(Cursor cursor);
    protected abstract ContentValues modelToContentValues(Model model);

    FishmashDAO(Context context, String table, String[] columns)
    {
        this.table = table;
        this.columns = columns;

        sqLiteDatabase = new FishmashOpener(context).getWritableDatabase();
    }

    public List<Model> selectAll()
    {
        List<Model> modelList = new LinkedList<>();

        Cursor cursor = sqLiteDatabase.query(table, columns, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Model model = cursorToModel(cursor);
            modelList.add(model);
            cursor.moveToNext();
        }

        cursor.close();

        return modelList;
    }

    public Model select(long id)
    {
        String[] idToSelect = new String[]{String.valueOf(id)};

        Cursor cursor = sqLiteDatabase.query(table, columns, "id = ?", idToSelect, null, null, null);

        cursor.moveToFirst();

        return cursorToModel(cursor);
    }

    public void insert(List<Model> modelList)
    {
        for(Model model : modelList)
        {
            insert(model);
        }
    }

    public void insert(Model model)
    {
        ContentValues contentValues = modelToContentValues(model);
        contentValues.put(columns[0], model.getId());

        sqLiteDatabase.insert(table, null, contentValues);
    }

    public void update(List<Model> modelList)
    {
        for(Model model : modelList)
        {
            update(model);
        }
    }

    public void update(Model model)
    {
        ContentValues contentValues = modelToContentValues(model);
        String[] updateId = new String[]{String.valueOf(model.getId())};

        sqLiteDatabase.update(table, contentValues, "id=?", updateId);
    }

    public void delete(long id)
    {
        try
        {
            sqLiteDatabase.delete(table, "id=" + id, null);
        }
        catch(SQLiteException e)
        {
            // if table does not exist, then there is nothing to delete - continue
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    public void delete(Model model)
    {
        FishmashModel fishmashModel = (FishmashModel) model;

        delete(fishmashModel.getId());
    }

    public long count()
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + table, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public void truncate()
    {
        try
        {
            sqLiteDatabase.delete(table, null, null);
        }
        catch(SQLiteException e)
        {
            // if table does not exist, then there is nothing to truncate - continue
            if(!e.getMessage().contains("no such table"))
            {
                throw e;
            }
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        sqLiteDatabase.close();
        super.finalize();
    }
}
