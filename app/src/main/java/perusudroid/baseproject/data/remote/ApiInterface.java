package perusudroid.baseproject.data.remote;

import perusudroid.baseproject.data.model.api.request.LoginRequest;
import perusudroid.baseproject.data.model.api.response.login.LoginResponse;
import perusudroid.baseproject.data.model.api.response.openlist.OpenListResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface{

    @POST("/v2/588d15f5100000a8072d2945")
    Single<LoginResponse> login(@Body LoginRequest request);


    @GET("/v2/5926c34212000035026871cd")
    Observable<OpenListResponse> getList();

}
