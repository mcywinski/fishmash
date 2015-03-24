package net.elenx.fishmash.daos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public interface DAOInterface<FishmashModel>
{
    List<FishmashModel> selectAll();
    FishmashModel select(long id);

    void insert(FishmashModel fishmashModel);
    void insert(List<FishmashModel> fishmashModelList);

    void update(FishmashModel fishmashModel);
    void update(List<FishmashModel> fishmashModelList);

    void delete(long id);
    void delete(FishmashModel fishmashModel);

    long count();
    void truncate();
    void close();

    FishmashModel cursorToModel(Cursor cursor);
    ContentValues modelToContentValues(FishmashModel fishmashModel);
    long getId(FishmashModel fishmashModel);
}
