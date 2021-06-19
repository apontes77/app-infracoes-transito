package br.com.pucgo.appTrafficViolations.retrofit;

import java.util.List;

import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interface que define o contrato de métodos a serem implementados nas chamadas HTTP associadas às infrações de trânsito
 */
public interface RestApiInterfaceTrafficViolation {

    @Multipart
    @POST("api/v1/traffic-violations/")
    Call<Void> insertTrafficViolation(@Part MultipartBody.Part file, @Part("json") RequestBody json);

    @DELETE("api/v1/traffic-violations/{id}")
    Call<Void> deleteTrafficViolation(@Path("id") Integer id);

    @Multipart
    @PUT("api/v1/traffic-violations")
    Call<Void> updateTrafficViolation(@Part MultipartBody.Part file, @Part("json") RequestBody json);

    @GET("api/v1/traffic-violations")
    Call<List<TrafficViolation>> listTrafficViolations();
}

