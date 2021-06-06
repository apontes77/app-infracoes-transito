package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.User;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceUser;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.validations.UserValidations;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RestApiInterfaceUser apiServiceUser;
    public static final String NOME_PREFERENCE = "INFORMACOES_LOGIN_AUTOMATICO";
    private EditText txtLogin;
    private EditText txtPassword;
    private TextView txtRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiServiceUser = RestApiClient.getClient().create(RestApiInterfaceUser.class);
        txtLogin = (EditText) findViewById(R.id.edt_Email_Login);
        txtPassword = (EditText) findViewById(R.id.edt_Senha_Login);
        txtRegister = (TextView) findViewById(R.id.txt_Cadastrar);

        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekUserInBackEnd();
            }
        });

        txtRegister.setMovementMethod(LinkMovementMethod.getInstance());
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUserRegisterScreen = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(goToUserRegisterScreen);
            }
        });
    }

    public void seekUserInBackEnd() {
         String login = txtLogin.getText().toString();
         String password = txtPassword.getText().toString();

         retrievesUserDataFromSharedPreferences();
        if (login.isEmpty() || password.isEmpty()) {
            txtLogin.setError("Insira o login");
            txtLogin.requestFocus();
            txtPassword.setError("Insira a senha");
            txtPassword.requestFocus();
            return;
        } else if (UserValidations.validateEmail(login) && UserValidations.validatePassword(password)) {
            User user = new User(login, password);
            Call<ResponseBody> callVerifyUser = apiServiceUser.loginUser(user);

            callVerifyUser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        saveUserDataInSharedPreferences();
                        redirectsToTrafficViolationsListing();
                    }
                    else {
                        GenerateToast.createShortToast(getApplicationContext(), "Usuário inexistente! Cadastre-se logo abaixo!"+response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    GenerateToast.createLongToast(getApplicationContext(), t.getMessage());
                }
            });
        }
        cleanFields();
    }

    public void saveUserDataInSharedPreferences() {
        SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();

        editor.putString("login", txtLogin.getText().toString());
        editor.putString("password", txtPassword.getText().toString());
        editor.apply();
    }

    public void retrievesUserDataFromSharedPreferences() {
        SharedPreferences data = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE);
        txtLogin.setText(data.getString("login", "Insira Login"));
        txtPassword.setText(data.getString("password", "Insira Senha"));
    }

    public void redirectsToTrafficViolationsListing() {
        Intent goToListing = new Intent(getApplicationContext(), ListTrafficViolation.class);
        startActivity(goToListing);
    }

    public void cleanFields() {
        txtLogin.setText("");
        txtPassword.setText("");
    }
}