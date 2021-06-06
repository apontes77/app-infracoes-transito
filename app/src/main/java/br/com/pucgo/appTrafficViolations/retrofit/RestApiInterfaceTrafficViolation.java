package br.com.pucgo.appTrafficViolations.retrofit;

import java.util.List;

import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiInterfaceTrafficViolation {

    @Multipart
    @POST("api/v1/traffic-violation/save")
    Call<TrafficViolation> insertTrafficViolation(
            @Part("data") String data,
            @Part("image_binary") RequestBody image);

    @DELETE("api/v1/traffic-violation/violation/{id}")
    Call<Void> deleteTrafficViolation(@Path("id") Integer id);

    @PATCH("api/v1/traffic-violation/violation")
    Call<Void> updateTrafficViolation(@Body TrafficViolation trafficViolation);

    @GET("api/v1/traffic-violation/all")
    Call<List<TrafficViolation>> listTrafficViolations();

    @GET("api/v1/traffic-violation/{id}")
    Call<TrafficViolation> getTrafficViolationByID(@Query("id") Integer id);
}

