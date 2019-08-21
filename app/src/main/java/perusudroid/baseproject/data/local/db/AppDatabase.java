package perusudroid.baseproject.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import perusudroid.baseproject.data.local.db.dao.IOpenListDao;
import perusudroid.baseproject.data.model.api.response.openlist.Data;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract IOpenListDao openListDao();

}
