package br.com.pucgo.appInfracoes.retrofit;

import java.util.List;

import br.com.pucgo.appInfracoes.modelos.Denuncia;
import br.com.pucgo.appInfracoes.modelos.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiInterfaceUsuario {
    @Headers("Content-Type: application/json")
    @POST("api/v1/usuarios/salvar")
    Call<Usuario> inserirUsuario(
            @Body Usuario usuario);

    @GET("api/v1/usuarios")
    Call<List<Usuario>> listarUsuarios ();

    @Headers("Content-Type: application/json")
    @POST("api/v1/usuarios/login")
    Call<ResponseBody> loginUsuario(@Body Usuario usuario);
}

