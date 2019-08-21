package perusudroid.baseproject.ui.opensourcelist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.data.model.api.response.openlist.Data;
import perusudroid.baseproject.data.model.api.response.openlist.OpenListResponse;
import perusudroid.baseproject.ui.base.BaseViewModel;

import java.util.List;

public class OpenListViewModel extends BaseViewModel<IOpenView> {

    private MutableLiveData<List<Data>> opListMutableLiveData;

    public OpenListViewModel(IDataManagerHelper mDataManager, SchedulerProvider schedulerProvider) {
        super(mDataManager, schedulerProvider);

    }

    void doFetchApiData() {
        doFetchAndStoreUsingFlatMap();
    }

    void getList() {

        if (opListMutableLiveData == null) {
            //handles orientation
            opListMutableLiveData = new MutableLiveData<>();
        }

        getCompositeDisposable().add(
                getDataManager().getLocalList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(openList -> {
                            if (openList != null && openList.size() > 0) {
                                opListMutableLiveData.postValue(openList);
                            } else {
                                doFetchAndStoreUsingFlatMap();
                            }

                        }, throwable -> {
                            getView().handleError(throwable);
                        })
        );
    }

    private void doFetchAndStoreUsingFlatMap() {

        getCompositeDisposable().add(
                getDataManager().getList()
                        .flatMap((OpenListResponse openListResponse) -> getDataManager().saveNewList(openListResponse.getData()))
                        .flatMap((Boolean aBoolean) -> {
                            if (aBoolean) return getDataManager().getLocalList();
                            return null;
                        })
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(openList -> {
                            if (openList != null && openList.size() > 0) {
                                opListMutableLiveData.postValue(openList);
                            } else {
                                getView().handleError(new Throwable("Empty results"));
                            }
                        }, throwable -> {
                            getView().handleError(throwable);
                        })
        );
    }


    private void doFetchApiList() {

        getCompositeDisposable().add(
                getDataManager().getList()
                        .subscribeOn(getSchedulerProvider().io()) // should be io
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(openListResponse -> {
                            if (openListResponse != null &&
                                    openListResponse.getData() != null
                            ) {
                                opListMutableLiveData.postValue(openListResponse.getData());
                            } else {
                                getView().handleError(new Throwable(openListResponse.getMessage()));
                            }
                        }, throwable -> {
                            getView().handleError(throwable);
                        })
        );
    }

    private void insertList(List<Data> mList) {
        getCompositeDisposable().add(
                getDataManager().saveNewList(mList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aBoolean -> {
                            Log.d(TAG, "insertList: inserted");
                        }, throwable -> {
                            Log.d(TAG, "insertList: error " + throwable.getLocalizedMessage());
                        }));
    }


    public MutableLiveData<List<Data>> getOpListMutableLiveData() {
        return opListMutableLiveData;
    }


}
