package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.User;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.validations.UserValidations;
import br.com.pucgo.appTrafficViolations.validations.ValidateCPF;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_user_register;
    private EditText email_register;
    private EditText cpf_register;
    private EditText password_register;
    private EditText repeated_password_register;
    private User user;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_register = findViewById(R.id.edt_Email_register);
        cpf_register =  findViewById(R.id.edt_cpf_register);
        password_register =  findViewById(R.id.edt_password_register);
        repeated_password_register = findViewById(R.id.edt_repeated_password_register);

        btn_user_register = findViewById(R.id.btn_user_register);
        btn_user_register.setOnClickListener(userRegister());
    }

    @NotNull
    private View.OnClickListener userRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_register.getText().toString().isEmpty() || cpf_register.getText().toString().isEmpty() || password_register.getText().toString().isEmpty()) {
                    email_register.requestFocus();
                    email_register.setError("Insira o email!");
                    cpf_register.requestFocus();
                    cpf_register.setError("Insira o cpf!");
                    password_register.requestFocus();
                    password_register.setError("Insira a senha!");
                }
                if (UserValidations.validateEmail(email_register.getText().toString())
                        && UserValidations.validatePassword(password_register.getText().toString(), repeated_password_register.getText().toString())
                        && ValidateCPF.isCPF(cpf_register.getText().toString())) {
                    insertUserPreferences();
                } else {
                    GenerateToast.createShortToast(getApplicationContext(), "Email ou senha inválidos! Tente novamente!");
                }
            }
        };
    }

    private void insertUserPreferences() {
        SharedPreferences prefs = getSharedPreferences("preferencias", MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();

        ed.putString("login", email_register.getText().toString());
        ed.putString("password", password_register.getText().toString());
        ed.putString("cpf", cpf_register.getText().toString());

        ed.apply();
        cleanFields();
        GenerateToast.createLongToast(getApplicationContext(), "Cadastro realizado com sucesso!");
        redirectsToLoginScreen();
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