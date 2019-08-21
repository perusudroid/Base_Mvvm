package perusudroid.baseproject.ui.photos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import io.reactivex.disposables.CompositeDisposable;
import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.data.model.api.response.stack.Items;
import perusudroid.baseproject.data.model.other.NetworkState;

public class ItemDataSource extends PageKeyedDataSource<Integer, Items> {

    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 50;
    private final String SITE = "stackoverflow";

    private final CompositeDisposable compositeDisposable;
    private final IDataManagerHelper iDataManagerHelper;
    private final SchedulerProvider schedulerProvider;
    private MutableLiveData<NetworkState> initLoad;

    public ItemDataSource(CompositeDisposable compositeDisposable, IDataManagerHelper iDataManagerHelper,
                          SchedulerProvider schedulerProvider, MutableLiveData<NetworkState> initLoad) {
        this.compositeDisposable = compositeDisposable;
        this.iDataManagerHelper = iDataManagerHelper;
        this.schedulerProvider = schedulerProvider;
        this.initLoad = initLoad;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Items> callback) {

        Log.d("ItemDataSource", "loadInitial: FIRST_PAGE " + FIRST_PAGE);

        if (compositeDisposable != null && iDataManagerHelper != null &&
                schedulerProvider != null
        ) {
            compositeDisposable.add(
                    iDataManagerHelper.getStackOverFlow(FIRST_PAGE, SITE)
                            .doOnSuccess(openListResponse -> {

                                    }
                            )
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(stackResponse -> {
                                if (stackResponse != null && stackResponse.getItems().size() > 0) {
                                    callback.onResult(stackResponse.getItems(), null, FIRST_PAGE + 1);
                                } else {
                                    initLoad.postValue(new NetworkState(NetworkState.Status.EMPTY, "Empty"));
                                }
                            }, throwable -> {
                                initLoad.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getLocalizedMessage()));
                            })
            );
        }


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Items> callback) {

        Log.d("ItemDataSource", "loadBefore: current page " + params.key);

        compositeDisposable.add(
                iDataManagerHelper.getStackOverFlow(params.key, SITE)
                        .doOnSuccess(openListResponse -> {
                                }
                        )
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(stackResponse -> {
                            if (stackResponse != null && stackResponse.getItems().size() > 0) {
                                Integer key = (params.key > 1) ? params.key - 1 : null;
                                callback.onResult(stackResponse.getItems(), key);
                            } else {
                                initLoad.postValue(new NetworkState(NetworkState.Status.EMPTY, "No More data"));
                            }
                        }, throwable -> {
                            initLoad.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getLocalizedMessage()));
                        })
        );
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Items> callback) {

        Log.d("ItemDataSource", "loadAfter: current page " + params.key);

        compositeDisposable.add(
                iDataManagerHelper.getStackOverFlow(params.key, SITE)
                        .doOnSuccess(openListResponse -> {
                                }
                        )
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(stackResponse -> {
                            if (stackResponse != null && stackResponse.getItems().size() > 0) {
                                Integer key = stackResponse.getHas_more() ? params.key + 1 : null;
                                callback.onResult(stackResponse.getItems(), key);
                            } else {
                                initLoad.postValue(new NetworkState(NetworkState.Status.NO_LOAD_MORE, "No more data"));
                            }
                        }, throwable -> {
                            initLoad.postValue(new NetworkState(NetworkState.Status.NO_LOAD_MORE, throwable.getLocalizedMessage()));
                        })
        );
    }
}
