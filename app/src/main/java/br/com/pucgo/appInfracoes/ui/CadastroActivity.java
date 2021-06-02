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

public class CadastroActivity extends AppCompatActivity {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String SENHA_PATTERN ="^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern patternEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern patternSenha = Pattern.compile(SENHA_PATTERN, Pattern.CASE_INSENSITIVE);

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
                if (validarEmail(email_cadastro.getText().toString()) 
                        && validarSenha(senha_cadastro.getText().toString(), senha_repetida_cadastro.getText().toString())) {
                    Intent voltaParaTelaDeLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(voltaParaTelaDeLogin);
                }
                else {
                    Toast.makeText(CadastroActivity.this, "deu algo errado", Toast.LENGTH_SHORT).show();
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

    public static boolean validarEmail(String email){
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
    
    public static boolean validarSenha(String senhaInserida, String senhaRepetida) {
        Matcher matcher = null;
        if(senhaInserida.equals(senhaRepetida)) { 
            matcher = patternSenha.matcher(senhaInserida);
        }
        return matcher.matches();
    }
}