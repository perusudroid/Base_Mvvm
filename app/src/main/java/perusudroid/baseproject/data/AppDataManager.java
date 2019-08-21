package perusudroid.baseproject.data;

import perusudroid.baseproject.data.local.prefs.IPrefHelper;
import perusudroid.baseproject.data.model.api.request.LoginRequest;
import perusudroid.baseproject.data.model.api.response.login.LoginResponse;
import perusudroid.baseproject.data.remote.ApiInterface;
import perusudroid.baseproject.data.remote.DeApiService;
import perusudroid.baseproject.data.local.db.IDBHelper;
import perusudroid.baseproject.data.model.api.response.openlist.Data;
import perusudroid.baseproject.data.model.api.response.openlist.OpenListResponse;
import perusudroid.baseproject.data.model.api.response.stack.StackResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements IDataManagerHelper {


    private final ApiInterface mApiInterface;
    private final DeApiService deApiService;
    private final IDBHelper iDbHelper;
    private final IPrefHelper iPrefHelper;

    @Inject
    public AppDataManager(ApiInterface mApiInterface, DeApiService deApiService, IDBHelper iDbHelper, IPrefHelper iPrefHelper) {
        this.mApiInterface = mApiInterface;
        this.deApiService = deApiService;
        this.iDbHelper = iDbHelper;
        this.iPrefHelper = iPrefHelper;
    }

    @Override
    public Single<LoginResponse> login(LoginRequest request) {
        return mApiInterface.login(request);
    }

    @Override
    public Observable<OpenListResponse> getList() {
        return mApiInterface.getList();
    }


    @Override
    public Observable<List<Data>> getLocalList() {
        return iDbHelper.getLocalList();
    }

    @Override
    public Observable<Boolean> addNewData(Data data) {
        return iDbHelper.addNewData(data);
    }

    @Override
    public Observable<Boolean> saveNewList(List<Data> dataList) {
        return iDbHelper.saveNewList(dataList);
    }

    @Override
    public Observable<Boolean> sample() {
        return iDbHelper.sample();
    }


    @Override
    public void updateLoginData(LoginResponse loginResponse) {
        iPrefHelper.updateLoginData(loginResponse);
    }

    @Override
    public String getAuthToken() {
        return iPrefHelper.getAuthToken();
    }

    @Override
    public Boolean isLoggedIn() {
        return iPrefHelper.isLoggedIn();
    }


    @Override
    public Single<StackResponse> getStackOverFlow(int page, String site) {
        return deApiService.getStackOverFlow(page, site);
    }
}
