package br.com.pucgo.appTrafficViolations.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * classe que configura a biblioteca Retrofit, usada para requisições HTTP.
 */
public class RestApiClient{

    public static Retrofit retrofit = null;

    public static final String BASE_URL = "http://10.0.2.2:8081/";

    /**
     * retorna uma instância do tipo Retrofit, com a URL-base configurada e com a biblioteca
     * Gson adaptada para realizar a serialização e desserialização de objetos.
     * @return
     */
    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                .create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


}

