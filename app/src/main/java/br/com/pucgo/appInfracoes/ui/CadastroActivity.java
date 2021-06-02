package br.com.pucgo.appInfracoes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Usuario;
import br.com.pucgo.appInfracoes.retrofit.RestApiClient;
import br.com.pucgo.appInfracoes.retrofit.RestApiInterfaceUsuario;
import br.com.pucgo.appInfracoes.utilidades.GeraToast;
import br.com.pucgo.appInfracoes.validacoes.ValidacoesDeUsuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    Button btn_cadastro_usuario;
    EditText email_cadastro;
    EditText senha_cadastro;
    EditText senha_repetida_cadastro;
    RestApiInterfaceUsuario apiServiceUsuario;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email_cadastro = (EditText) findViewById(R.id.edt_Email_cadastro);
        senha_cadastro = (EditText) findViewById(R.id.edt_Senha_cadastro);
        senha_repetida_cadastro = (EditText) findViewById(R.id.edt_Senha_repetida_cadastro);
        apiServiceUsuario = RestApiClient.getClient().create(RestApiInterfaceUsuario.class);

        btn_cadastro_usuario = (Button) findViewById(R.id.btn_cadastro_usuario);
        btn_cadastro_usuario.setOnClickListener(clickCadastroUsuario());
    }

    @NotNull
    private View.OnClickListener clickCadastroUsuario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidacoesDeUsuario.validarEmail(email_cadastro.getText().toString())
                        && ValidacoesDeUsuario.validarSenha(senha_cadastro.getText().toString(), senha_repetida_cadastro.getText().toString())) {
                    usuario = new Usuario(email_cadastro.getText().toString(), senha_cadastro.getText().toString());
                    inserirUsuarioApi(usuario);
                } else {
                    GeraToast.criaToastCurto(getApplicationContext(), "Email ou senha inválidos! Tente novamente!");

                }
            }
        };
    }

    public void inserirUsuarioApi(Usuario usuario) {
        Call<Usuario> chamadaInserirUsuario = apiServiceUsuario.inserirUsuario(usuario);
        chamadaInserirUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    GeraToast.criaToastCurto(getApplicationContext(), response.body().toString());
                    redirecionaParaTelaLogin();
                }
                else {
                    GeraToast.criaToastCurto(getApplicationContext(), "Algo deu errado com o servidor! Tente novamente.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                GeraToast.criaToastCurto(getApplicationContext(), "Sem conexão! Tente novamente.");
                call.cancel();
            }
        });
        limpaCampos();
    }

    private void redirecionaParaTelaLogin() {
        Intent voltaParaTelaDeLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(voltaParaTelaDeLogin);
    }

    public void limpaCampos() {
        email_cadastro.setText("");
        senha_cadastro.setText("");
        senha_repetida_cadastro.setText("");
    }
}