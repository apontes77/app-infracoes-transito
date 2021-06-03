package br.com.pucgo.appInfracoes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Usuario;
import br.com.pucgo.appInfracoes.retrofit.RestApiClient;
import br.com.pucgo.appInfracoes.retrofit.RestApiInterfaceUsuario;
import br.com.pucgo.appInfracoes.utilidades.GeraToast;
import br.com.pucgo.appInfracoes.validacoes.ValidacoesDeUsuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    RestApiInterfaceUsuario apiServiceUsuario;
    EditText txtLogin;
    EditText txtSenha;
    TextView txtCadastrar;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiServiceUsuario = RestApiClient.getClient().create(RestApiInterfaceUsuario.class);
        txtLogin = (EditText) findViewById(R.id.edt_Email_Login);
        txtSenha = (EditText) findViewById(R.id.edt_Senha_Login);
        txtCadastrar = (TextView) findViewById(R.id.txt_Cadastrar);

        btnEntrar = (Button) findViewById(R.id.btn_Login);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuarioNaBaseDeDados();
            }
        });

        txtCadastrar.setMovementMethod(LinkMovementMethod.getInstance());
        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeCadastroDeUsuario = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(vaiParaTelaDeCadastroDeUsuario);
            }
        });
    }

    public void buscarUsuarioNaBaseDeDados() {

        final String login = txtLogin.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        if (login.isEmpty() || senha.isEmpty()) {
            txtLogin.setError("Insira o login");
            txtLogin.requestFocus();
            txtSenha.setError("Insira a senha");
            txtSenha.requestFocus();
            return;
        } else if (ValidacoesDeUsuario.validarEmail(login) && ValidacoesDeUsuario.validarSenha(senha)) {
            Call<ResponseBody> chamadaVerificarUsuario = apiServiceUsuario
                                                                        .loginUsuario(new Usuario(login, senha));
            chamadaVerificarUsuario.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 204) {
                        GeraToast.criaToastCurto(getApplicationContext(), "Login correto");
                        redirecionaParaListagemDeDenuncias();
                    }
                    else {
                        GeraToast.criaToastCurto(getApplicationContext(), "Usuário inexistente! Cadastre-se logo abaixo!");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    GeraToast.criaToastLongo(getApplicationContext(), t.getMessage());
                }
            });
        }
        limpaCampos();
    }

    public void redirecionaParaListagemDeDenuncias() {
        Intent vaiParaListagem = new Intent(getApplicationContext(), ListarDenuncias.class);
        startActivity(vaiParaListagem);
    }

    public void limpaCampos() {
        txtLogin.setText("");
        txtSenha.setText("");
    }
}