package br.com.pucgo.appInfracoes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.pucgo.appInfracoes.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtLogin;
    EditText txtSenha;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = (EditText) findViewById(R.id.edt_Email_Login);
        txtSenha = (EditText) findViewById(R.id.edt_Senha_Login);

        btnEntrar = (Button) findViewById(R.id.btn_Login);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();

                ed.putString("login", txtLogin.getText().toString());
                ed.putString("senha", txtSenha.getText().toString());

                ed.apply();
            }
        });

    }
}