package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.User;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceUser;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.validations.UserValidations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_cadastro_usuario;
    private EditText email_cadastro;
    private EditText senha_cadastro;
    private EditText senha_repetida_cadastro;
    private RestApiInterfaceUser apiServiceUsuario;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email_cadastro = (EditText) findViewById(R.id.edt_Email_cadastro);
        senha_cadastro = (EditText) findViewById(R.id.edt_Senha_cadastro);
        senha_repetida_cadastro = (EditText) findViewById(R.id.edt_Senha_repetida_cadastro);
        apiServiceUsuario = RestApiClient.getClient().create(RestApiInterfaceUser.class);

        btn_cadastro_usuario = (Button) findViewById(R.id.btn_cadastro_usuario);
        btn_cadastro_usuario.setOnClickListener(clickCadastroUsuario());
    }

    @NotNull
    private View.OnClickListener clickCadastroUsuario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserValidations.validateEmail(email_cadastro.getText().toString())
                        && UserValidations.validatePassword(senha_cadastro.getText().toString(), senha_repetida_cadastro.getText().toString())) {
                    String email = email_cadastro.getText().toString();
                    String senha = senha_cadastro.getText().toString();
                    user = new User().builder().login(email).password(senha).build();
                    inserirUsuarioApi(user);
                } else {
                    GenerateToast.createShortToast(getApplicationContext(), "Email ou senha inválidos! Tente novamente!");

                }
            }
        };
    }

    public void inserirUsuarioApi(User user) {
        Call<User> chamadaInserirUsuario = apiServiceUsuario.insertUser(user);
        chamadaInserirUsuario.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v("resp", response.body().toString());

                User userResponse = response.body();
                if (response.isSuccessful() && userResponse !=null) {
                    GenerateToast.createLongToast(getApplicationContext(), "Cadastro realizado com sucesso!");
                    redirecionaParaTelaLogin();
                }
                else {
                    GenerateToast.createShortToast(getApplicationContext(), "Não foi possível cadastrar! Tente de novo!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("fail", t.toString());

                GenerateToast.createShortToast(getApplicationContext(), "Sem conexão! Tente novamente.");
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