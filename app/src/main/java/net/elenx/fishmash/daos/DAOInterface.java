package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

interface DAOInterface<Model>
{
    List<Model> selectAll();
    Model select(long id);

    void insert(Model model);
    void insert(List<Model> modelList);

    void update(Model model);
    void update(List<Model> modelList);

    void delete(long id);
    void delete(Model model);

    long count();
    void truncate();
    void close();

    Model cursorToModel(Cursor cursor);
    ContentValues modelToContentValues(Model model);
    long getIdOf(Model model);
}
