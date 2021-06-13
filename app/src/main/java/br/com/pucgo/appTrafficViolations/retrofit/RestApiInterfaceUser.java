package br.com.pucgo.appTrafficViolations.retrofit;

import java.util.List;

import br.com.pucgo.appTrafficViolations.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * interface que define o contrato de métodos a serem implementados nas chamadas HTTP associadas às operações de manipulação de Usuário.
 */
public interface RestApiInterfaceUser {
    @Headers("Content-Type: application/json")
    @POST("api/v1/users/save")
    Call<User> insertUser(@Body User user);

    @GET("api/v1/users")
    Call<List<User>> listUsers();

    @GET("api/v1/users/user")
    Call<Void> getUserID(@Body User user);

    @POST("api/v1/users/login")
    Call<User> loginUser(@Body User user);
}

