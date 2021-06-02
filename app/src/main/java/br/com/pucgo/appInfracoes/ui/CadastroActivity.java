package br.com.pucgo.appInfracoes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.utilidades.GeraToast;
import br.com.pucgo.appInfracoes.validacoes.ValidacoesDeUsuario;

public class CadastroActivity extends AppCompatActivity {

    Button btn_cadastro_usuario;
    EditText email_cadastro;
    EditText senha_cadastro;
    EditText senha_repetida_cadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email_cadastro = (EditText) findViewById(R.id.edt_Email_cadastro);
        senha_cadastro = (EditText) findViewById(R.id.edt_Senha_cadastro);
        senha_repetida_cadastro = (EditText) findViewById(R.id.edt_Senha_repetida_cadastro);
        btn_cadastro_usuario = (Button) findViewById(R.id.btn_cadastro_usuario);

        btn_cadastro_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidacoesDeUsuario.validarEmail(email_cadastro.getText().toString())
                        && ValidacoesDeUsuario.validarSenha(senha_cadastro.getText().toString(), senha_repetida_cadastro.getText().toString())) {
                    GeraToast.criaToastCurto(getApplicationContext(), "Cadastro Realizado com Sucesso!");
                    Intent voltaParaTelaDeLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(voltaParaTelaDeLogin);
                }
                else {
                    GeraToast.criaToastCurto(getApplicationContext(), "Algo deu errado!");
                    limpaCampos();
                }

            }
        });
    }

    private void limpaCampos() {
        email_cadastro.setText("");
        senha_cadastro.setText("");
        senha_repetida_cadastro.setText("");
    }
}