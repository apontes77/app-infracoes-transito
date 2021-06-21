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

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.validations.UserValidations;

/**
 * classe que manipula o login de usuário
 */
public class LoginActivity extends AppCompatActivity {

    public static final String NAME_PREFERENCES = "INFORMACOES_LOGIN_AUTOMATICO";
    private EditText txtLogin;
    private EditText txtPassword;
    private TextView txtRegister;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = findViewById(R.id.edt_Email_Login);
        txtPassword = findViewById(R.id.edt_Password_Login);
        txtRegister = findViewById(R.id.txt_register);

        btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekUserDataInPreferences();
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

    /**
     * redireciona o usuário para a listagem
     */
    private void moveToListViolationsActivity() {
        Intent intent = new Intent(LoginActivity.this, ListTrafficViolation.class);
        startActivity(intent);
    }

    /**
     * busca os dados do usuários no SharedPreferences
     */
    public void seekUserDataInPreferences() {

        String login = txtLogin.getText().toString();
        String password = txtPassword.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            txtLogin.setError("Insira o login");
            txtLogin.requestFocus();
            txtPassword.setError("Insira a senha");
            txtPassword.requestFocus();
            return;
        } else if (UserValidations.validateEmail(login) && UserValidations.validatePassword(password)) {
            SharedPreferences sharedPreferences = getSharedPreferences("preferencias", MODE_PRIVATE);
            String login1 = sharedPreferences.getString("login", "");
            String password1 = sharedPreferences.getString("password", "");
            Log.v("LOG", login1);
            Log.v("PAS", password1);

            if(login.equals(login1) && password.equals(password1) ) {
                moveToListViolationsActivity();
            }
            else {
                GenerateToast.createLongToast(LoginActivity.this, "Usuário inexistente! Tente novamente.");
            }
        }
        else {
            GenerateToast.createLongToast(LoginActivity.this, "Lembre que o email não pode ser vazio e que a senha deve ter pelo menos uma letra maiúscula, um caractere e, no mínimo, 7 caracteres.");
        }
        cleanFields();
    }

    /**
     * limpa campos de texto
     */
    public void cleanFields() {
        txtLogin.setText("");
        txtPassword.setText("");
    }
}