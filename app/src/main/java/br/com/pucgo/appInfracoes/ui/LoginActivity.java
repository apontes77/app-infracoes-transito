package br.com.pucgo.appInfracoes.ui;

import android.content.Context;
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

import java.util.logging.Logger;

import br.com.pucgo.appInfracoes.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOG";
    EditText txtLogin;
    EditText txtSenha;
    TextView txtCadastrar;

    Button btnEntrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = (EditText) findViewById(R.id.edt_Email_Login);
        txtSenha = (EditText) findViewById(R.id.edt_Senha_Login);
        txtCadastrar = (TextView) findViewById(R.id.txt_Cadastrar);
        txtCadastrar.setMovementMethod(LinkMovementMethod.getInstance());
        chamarCadastro();
        btnEntrar = (Button) findViewById(R.id.btn_Login);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();

                ed.putString("login", txtLogin.getText().toString());
                ed.putString("senha", txtSenha.getText().toString());

                ed.apply();

                //Log.i(TAG, preferences.getString(getString(R.string.app_name), "SHARED"));

                Intent vaiParaTelaDeListagemDeInfracoes = new Intent(getApplicationContext(), ListarDenuncias.class);
                startActivity(vaiParaTelaDeListagemDeInfracoes);
            }
        });

    }

    public void chamarCadastro() {

        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeCadastroDeUsuario = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(vaiParaTelaDeCadastroDeUsuario);
            }
        });

    }


}