package br.com.pucgo.appTrafficViolations.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.pucgo.appTrafficViolations.R;

/**
 * tela de aviso de erro de algum processo do app
 */
public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_dialog);


    }
}