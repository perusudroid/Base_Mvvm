package perusudroid.baseproject.ui.base;

import androidx.lifecycle.ViewModel;

import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<V extends IView> extends ViewModel {


    protected String TAG = BaseViewModel.class.getSimpleName();

    private final IDataManagerHelper mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<V> mView;

    public BaseViewModel(IDataManagerHelper mDataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = mDataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public IDataManagerHelper getDataManager() {
        return mDataManager;
    }

    public V getView() {
        return mView.get();
    }

    public void setView(V navigator) {
        this.mView = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
