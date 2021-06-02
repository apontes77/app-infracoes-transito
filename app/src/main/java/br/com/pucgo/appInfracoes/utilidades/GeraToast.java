package br.com.pucgo.appInfracoes.utilidades;

import android.content.Context;
import android.widget.Toast;

import br.com.pucgo.appInfracoes.ui.CadastroActivity;

public class GeraToast {

    public static void criaToastCurto(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static void criaToastLongo(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}
