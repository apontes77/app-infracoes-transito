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

    private Button btn_user_register;
    private EditText email_register;
    private EditText password_register;
    private EditText repeated_password_register;
    private RestApiInterfaceUser apiUserService;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email_register = (EditText) findViewById(R.id.edt_Email_register);
        password_register = (EditText) findViewById(R.id.edt_password_register);
        repeated_password_register = (EditText) findViewById(R.id.edt_repeated_password_register);
        apiUserService = RestApiClient.getClient().create(RestApiInterfaceUser.class);

        btn_user_register = (Button) findViewById(R.id.btn_user_register);
        btn_user_register.setOnClickListener(userRegister());
    }

    @NotNull
    private View.OnClickListener userRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserValidations.validateEmail(email_register.getText().toString())
                        && UserValidations.validatePassword(password_register.getText().toString(), repeated_password_register.getText().toString())) {
                    String email = email_register.getText().toString();
                    String senha = password_register.getText().toString();
                    user = new User().builder().login(email).password(senha).build();
                    insertUserAPI(user);
                } else {
                    GenerateToast.createShortToast(getApplicationContext(), "Email ou senha inválidos! Tente novamente!");

                }
            }
        };
    }

    public void insertUserAPI(User user) {
        Call<User> callInsertUser = apiUserService.insertUser(user);
        callInsertUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v("resp", response.body().toString());

                User userResponse = response.body();
                if (response.isSuccessful() && userResponse !=null) {
                    GenerateToast.createLongToast(getApplicationContext(), "Cadastro realizado com sucesso!");
                    redirectsToLoginScreen();
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
        cleanFields();
    }

    private void redirectsToLoginScreen() {
        Intent backToLoginScreen = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(backToLoginScreen);
    }

    public void cleanFields() {
        email_register.setText("");
        password_register.setText("");
        repeated_password_register.setText("");
    }
}