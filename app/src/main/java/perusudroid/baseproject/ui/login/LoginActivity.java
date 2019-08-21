package perusudroid.baseproject.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.trisysit.baseproject_mvvm.R;
import com.trisysit.baseproject_mvvm.databinding.ActivityLoginBinding;

import perusudroid.baseproject.ui.opensourcelist.OpenListActivity;
import perusudroid.baseproject.ui.base.BaseActivity;
import perusudroid.baseproject.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements ILoginView, View.OnClickListener {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;


    @Override
    protected void setAssets() {
        mActivityLoginBinding.btnGo.setOnClickListener(this);
    }


    @Override
    protected void initStuffs() {
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setView(this);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    public void onSuccessApi() {
        super.onSuccessApi();
        startActivity(new Intent(this, OpenListActivity.class));
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGo:
                if (isValidated()) {
                    mLoginViewModel.doLogin(mActivityLoginBinding.etName.getText().toString().trim(), mActivityLoginBinding.etPass.getText().toString().trim());
                }
                break;
        }
    }

    private boolean isValidated() {
        if (TextUtils.isEmpty(mActivityLoginBinding.etName.getText().toString())) {
            return false;
        }

        if (TextUtils.isEmpty(mActivityLoginBinding.etPass.getText().toString())) {
            return false;
        }
        return true;
    }
}
