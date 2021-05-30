package br.com.pucgo.appInfracoes.retrofit;

import java.util.List;

import br.com.pucgo.appInfracoes.modelos.Denuncia;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiInterfaceDenuncia {

    @Headers("Content-Type: application/json")
    @POST("/denuncias")
    Call<Denuncia> inserirDenuncias(
            @Body Denuncia denuncia);

    @Headers("Content-Type: application/json")
    @DELETE("/denuncias/{id}")
    Call<Void> excluirDenuncia (@Path("id") Integer id);

    @PATCH("/denuncias")
    Call<Void> atualizarDenuncia (@Body Denuncia denuncia);

    @GET("/denuncias")
    Call<List<Denuncia>> listarDenuncias ();

    @GET("/denuncias/{id}")
    Call<Denuncia> obterDenuncia(@Query("id") Integer id);
}

