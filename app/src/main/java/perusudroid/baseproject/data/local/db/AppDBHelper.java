package perusudroid.baseproject.data.local.db;

import perusudroid.baseproject.data.model.api.response.openlist.Data;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDBHelper implements IDBHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDBHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<List<Data>> getLocalList() {
        return Observable.fromCallable(new Callable<List<Data>>() {
            @Override
            public List<Data> call() throws Exception {
                return mAppDatabase.openListDao().getList();
            }
        });
    }

    @Override
    public Observable<Boolean> addNewData(Data data) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                mAppDatabase.openListDao().addNewData(data);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveNewList(List<Data> dataList) {

        if(!(dataList.size() > 0)){
            return Observable.just(false);
        }

        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                mAppDatabase.openListDao().insertAll(dataList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> sample() {
        return Observable.just(true);
    }
}
