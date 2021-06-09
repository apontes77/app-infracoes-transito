package br.com.pucgo.appTrafficViolations.retrofit;

import java.util.List;

import br.com.pucgo.appTrafficViolations.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestApiInterfaceUser {
    @Headers("Content-Type: application/json")
    @POST("api/v1/users/save")
    Call<User> insertUser(@Body User user);

    @GET("api/v1/users")
    Call<List<User>> listUsers();

    @POST("api/v1/users/login")
    Call<ResponseBody> loginUser(@Body User user);
}
