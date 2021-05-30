package br.com.pucgo.appInfracoes.ui;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Modals extends AppCompatActivity {

    public void modalSucesso(String titulo, String textoPositive, String textoNegative) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        // alert.setMessage("Message");

        alert.setPositiveButton(textoPositive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.setNegativeButton(textoNegative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }
}
