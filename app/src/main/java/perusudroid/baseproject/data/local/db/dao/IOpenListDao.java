package perusudroid.baseproject.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import perusudroid.baseproject.data.model.api.response.openlist.Data;

import java.util.List;

@Dao
public interface IOpenListDao {


    @Query("SELECT * from data")
    List<Data> getList();

    @Query("SELECT * from data WHERE id = (:id)")
    Data getListById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNewData(Data data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Data> dataList);

    @Delete
    void deleteData(Data data);

}
