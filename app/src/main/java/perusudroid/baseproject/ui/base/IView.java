package perusudroid.baseproject.ui.base;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public interface IView<V> {

    @LayoutRes
    int getLayoutId();

    void showMessage(String message);

    void showProgress();

    void dismissProgress();

    void onSuccessApi();

    void setRecyclerAdapter(RecyclerView.Adapter recyclerAdapter);

    void handleError(Throwable throwable);
}
