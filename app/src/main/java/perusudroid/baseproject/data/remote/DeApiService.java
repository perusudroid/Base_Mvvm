package perusudroid.baseproject.data.remote;

import perusudroid.baseproject.data.model.api.response.stack.StackResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeApiService {


    @GET("answers")
    Single<StackResponse> getStackOverFlow(@Query("page") int page,
                                           @Query("site") String site);

}
