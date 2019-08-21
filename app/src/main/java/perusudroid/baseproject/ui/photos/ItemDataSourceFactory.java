package perusudroid.baseproject.ui.photos;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.data.model.api.response.stack.Items;
import perusudroid.baseproject.data.model.other.NetworkState;

import io.reactivex.disposables.CompositeDisposable;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Items>> itemLiveDataSource = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable;
    private final IDataManagerHelper iDataManagerHelper;
    private final SchedulerProvider schedulerProvider;
    private final MutableLiveData<NetworkState> initLoad;


    public ItemDataSourceFactory(CompositeDisposable compositeDisposable, IDataManagerHelper iDataManagerHelper,
                                 SchedulerProvider schedulerProvider, MutableLiveData<NetworkState> initLoad) {
        this.compositeDisposable = compositeDisposable;
        this.iDataManagerHelper = iDataManagerHelper;
        this.schedulerProvider = schedulerProvider;
        this.initLoad = initLoad;
    }

    @Override
    public DataSource<Integer, Items> create() {
        ItemDataSource itemDataSource = new ItemDataSource(compositeDisposable, iDataManagerHelper, schedulerProvider, initLoad);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Items>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
