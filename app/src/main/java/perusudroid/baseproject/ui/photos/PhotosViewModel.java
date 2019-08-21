package perusudroid.baseproject.ui.photos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import perusudroid.baseproject.ui.base.BaseViewModel;
import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.data.model.api.response.stack.Items;
import perusudroid.baseproject.data.model.other.NetworkState;

public class PhotosViewModel extends BaseViewModel<IPhotosView> {

    LiveData<PagedList<Items>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Items>> liveDataSource;

    MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    public PhotosViewModel(IDataManagerHelper mDataManager, SchedulerProvider schedulerProvider) {
        super(mDataManager, schedulerProvider);

       doConfigureAndFetch();

    }

    public void doConfigureAndFetch() {
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(getCompositeDisposable(), getDataManager(), getSchedulerProvider(), initialLoad);
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(10)
                        .build();

        //noinspection unchecked
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }
}


