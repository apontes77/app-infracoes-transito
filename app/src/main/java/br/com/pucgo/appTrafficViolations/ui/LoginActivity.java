package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.User;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceUser;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.validations.UserValidations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RestApiInterfaceUser apiServiceUser;
    public static final String NAME_PREFERENCES = "INFORMACOES_LOGIN_AUTOMATICO";
    private EditText txtLogin;
    private EditText txtPassword;
    private TextView txtRegister;
    private Button btnLogin;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);

        apiServiceUser = RestApiClient.getClient().create(RestApiInterfaceUser.class);
        txtLogin = findViewById(R.id.edt_Email_Login);
        txtPassword = findViewById(R.id.edt_Password_Login);
        txtRegister = findViewById(R.id.txt_register);


        btnLogin = findViewById(R.id.btn_Login);
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

        if (login.isEmpty() || password.isEmpty()) {
            txtLogin.setError("Insira o login");
            txtLogin.requestFocus();
            txtPassword.setError("Insira a senha");
            txtPassword.requestFocus();
            return;
        } else if (UserValidations.validateEmail(login) && UserValidations.validatePassword(password)) {
            User user = new User(login, password);
            Call<User> callVerifyUser = apiServiceUser.loginUser(user);

            callVerifyUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User userResponse = response.body();
                    if (response.isSuccessful() && userResponse != null) {
                        saveUserDataInSharedPreferences();
                        redirectsToTrafficViolationsListing();
                    }
                    else {
                        GenerateToast.createShortToast(getApplicationContext(), "Usuário inexistente! Cadastre-se logo abaixo!"+response.body());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    GenerateToast.createLongToast(getApplicationContext(), t.getMessage());
                }
            });
        }
        cleanFields();
    }

    public void saveUserDataInSharedPreferences() {
        String login = txtLogin.getText().toString();
        String password = txtPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.apply();
    }

    public void retrieveUserData() {
        preferences = getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);
       if(preferences.contains("login")){
           txtLogin.setText(preferences.getString("login", "asdas"));
       }
       if(preferences.contains("password")) {
           txtPassword.setText(preferences.getString("password", "asasddas"));
       }
    }

    public void redirectsToTrafficViolationsListing() {
        Intent goToListing = new Intent(LoginActivity.this, ListTrafficViolation.class);
        startActivity(goToListing);
    }

    public void cleanFields() {
        txtLogin.setText("");
        txtPassword.setText("");
    }
}