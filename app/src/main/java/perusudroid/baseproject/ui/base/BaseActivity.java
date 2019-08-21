package perusudroid.baseproject.ui.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.trisysit.baseproject_mvvm.R;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements IView, BaseFragment.Callback, IBaseRecyclerAdapterListener {


    protected String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private T mViewDataBinding;
    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preInit();
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        postInit();
        getInputs(getIntent().getExtras());
        performDataBinding();
        setAssets();
    }

    protected void getInputs(Bundle extras) {

    }

    protected abstract void setAssets();

    protected void preInit() {

    }


    protected void postInit() {

    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getValidatedLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.executePendingBindings();
        initStuffs();
    }

    private int getValidatedLayoutId() {
        if (getLayoutId() == 0) {
            throw new RuntimeException("Forgot to pass layout id @getLayoutId");
        }
        return getLayoutId();
    }


    protected abstract void initStuffs();

    public abstract V getViewModel();

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);

        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessApi() {

    }

    @Override
    public void setRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void onClickItem(Object data) {

    }

    @Override
    public void onRetryClicked() {

    }
}
