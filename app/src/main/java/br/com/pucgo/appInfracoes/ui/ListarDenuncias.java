package br.com.pucgo.appInfracoes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Denuncia;
import br.com.pucgo.appInfracoes.ui.adapter.ListaDenunciasAdapter;

public class ListarDenuncias extends AppCompatActivity implements ListaDenunciasAdapter.OnNoteListener {

    RecyclerView recyclerView;
    TextView tituloDenuncia;
    TextView descricaoDenuncia;
    FloatingActionButton fab;
    ImageView imagem;
    List<Denuncia> denuncias = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_denuncias);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listarDenuncias);
        tituloDenuncia = (TextView) findViewById(R.id.textView_titulo);
        descricaoDenuncia = (TextView) findViewById(R.id.textView_descricao);
        imagem = (ImageView) findViewById(R.id.imageView_1);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityManipularDenuncia());

        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",""));


        ListaDenunciasAdapter adapter = new ListaDenunciasAdapter(this, denuncias, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @NotNull
    private View.OnClickListener initiateActivityManipularDenuncia() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InserirDenuncia.class);
                startActivity(i);
            }
        };
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, EditarExcluirDenuncia.class);
        startActivity(intent);
    }
}