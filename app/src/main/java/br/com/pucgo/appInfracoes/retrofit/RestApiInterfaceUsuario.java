package br.com.pucgo.appInfracoes.retrofit;

import java.util.List;

import br.com.pucgo.appInfracoes.modelos.Denuncia;
import br.com.pucgo.appInfracoes.modelos.Usuario;
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
    @POST("usuarios/salvar")
    Call<Usuario> inserirUsuario(
            @Body Usuario usuario);

    @GET("/usuarios")
    Call<List<Usuario>> listarUsuarios ();

    @GET("/usuarios/{id}")
    Call<Usuario> obterUsuarios(@Query("id") Integer id);
}

