package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.pucgo.appTrafficViolations.R;

public class EditAndDeleteTrafficViolation extends AppCompatActivity {

    private EditText tituloListagem;
    private EditText descricaoListagem;
    private ImageView fotoDenunciaSelecionada;
    private Button editarDenuncia;
    private Button excluirDenuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_excluir_denuncia);
        Bundle bundle = getIntent().getExtras();
        String titulo = bundle.getString("titulo");
        String descricao = bundle.getString("descricao");

        tituloListagem = (EditText) findViewById(R.id.edtText_titulo_denuncia_da_listagem);
        tituloListagem.setText(titulo);
        tituloListagem.setEnabled(false);


        descricaoListagem = (EditText) findViewById(R.id.edtText_descricao_denuncia_da_listagem);
        descricaoListagem.setText(descricao);
        descricaoListagem.setEnabled(false);

        fotoDenunciaSelecionada = (ImageView) findViewById(R.id.imageView_carregarImagemDaDenuncia_Listagem);
        editarDenuncia = (Button) findViewById(R.id.button_editar_denuncia);
        excluirDenuncia = (Button) findViewById(R.id.button_excluir_denuncia);




    }
}