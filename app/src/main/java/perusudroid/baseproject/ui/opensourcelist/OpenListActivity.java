package perusudroid.baseproject.ui.opensourcelist;

import android.util.Log;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.trisysit.baseproject_mvvm.R;
import com.trisysit.baseproject_mvvm.databinding.ActivityOpenListBinding;
import perusudroid.baseproject.ui.base.BaseActivity;
import perusudroid.baseproject.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class OpenListActivity extends BaseActivity<ActivityOpenListBinding, OpenListViewModel> implements IOpenView {

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    LinearLayoutManager manager;
    OpenSourceAdapter openSourceAdapter;


    private OpenListViewModel viewModel;
    private ActivityOpenListBinding binding;


    @Override
    protected void initStuffs() {
        binding = getViewDataBinding();
        openSourceAdapter = new OpenSourceAdapter();
        openSourceAdapter.setListener(this);
        viewModel.setView(this);
        viewModel.getList();
    }

    @Override
    protected void setAssets() {
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(openSourceAdapter);

        viewModel.getOpListMutableLiveData().observe(this, dataList -> {
            if (dataList != null && dataList.size() > 0) {
                openSourceAdapter.resetItems(dataList);
            } else {
                openSourceAdapter.addEmptyView();
            }
        });
    }

    @Override
    public OpenListViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OpenListViewModel.class);
        return viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_list;
    }

    @Override
    public void handleError(Throwable throwable) {
        Log.e(TAG, "handleError: throwable " + throwable.getLocalizedMessage());
        if (openSourceAdapter != null) {
            openSourceAdapter.addEmptyView();
        }
    }

    @Override
    public void onRetryClicked() {
        super.onRetryClicked();
        showMessage("onRetryClicked");
        viewModel.doFetchApiData();
    }
}
