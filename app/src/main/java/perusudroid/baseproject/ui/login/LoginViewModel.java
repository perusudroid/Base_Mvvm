package perusudroid.baseproject.ui.login;


import perusudroid.baseproject.data.model.api.request.LoginRequest;
import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<ILoginView> {

    public LoginViewModel(IDataManagerHelper mDataManager, SchedulerProvider schedulerProvider) {
        super(mDataManager, schedulerProvider);
    }

    void doLogin(String email, String password) {

        getCompositeDisposable().add(
                getDataManager().login(new LoginRequest(email, password))
                        .doOnSuccess(loginResponse -> {
                                    getDataManager().updateLoginData(loginResponse);
                                }
                        )
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(loginResponse -> {
                            getView().onSuccessApi();
                        }, throwable -> {
                            getView().handleError(throwable);
                        })
        );
    }
}
