package perusudroid.baseproject.data.local.db;

import perusudroid.baseproject.data.model.api.response.openlist.Data;

import java.util.List;

import io.reactivex.Observable;

public interface IDBHelper {

    Observable<List<Data>> getLocalList();

    Observable<Boolean> addNewData(final Data data);

    Observable<Boolean> saveNewList(List<Data> dataList);

    Observable<Boolean> sample();

}
