package mehroz.sis.com.androiddevelopmenttest.rest;

import java.util.List;

import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserFollower;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("users/{username}")
    Call<UserResponse> getSingleUser(@Path("username") String userName);

    @GET("users/{username}/followers")
    Call<List<UserFollower>> getUserFollowerList(@Path("username") String name);

}