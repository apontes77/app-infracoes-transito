package br.com.pucgo.appInfracoes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Denuncia;
import br.com.pucgo.appInfracoes.ui.adapter.ListaDenunciasAdapter;

public class ListarDenuncias extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tituloDenuncia;
    TextView descricaoDenuncia;
    FloatingActionButton fab;
    private final String TAG = "ERRO AO CARREGAR";
    List<Denuncia> denuncias = new ArrayList<>();
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_denuncias);
        denuncias.add(new Denuncia("RACHADOR",
                    "rachador na av portugal",
                    "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));
        denuncias.add(new Denuncia("RACHADOR",
                "rachador na av portugal",
                "https://imagens-app-infracoes.s3.us-east-2.amazonaws.com/fila-carros.jpg"));


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listarDenuncias);
        tituloDenuncia = (TextView) findViewById(R.id.textView_titulo);
        descricaoDenuncia = (TextView) findViewById(R.id.textView_descricao);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityManipularDenuncia());
        loadImg();

        ListaDenunciasAdapter adapter = new ListaDenunciasAdapter(this, denuncias);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @NotNull
    private View.OnClickListener initiateActivityManipularDenuncia() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManipularDenuncia.class);
                startActivity(i);
            }
        };
    }

    public void loadImg() {
        new Thread() {
            @Override
            public void run() {
                Bitmap img = null;

                for (Denuncia denuncia:denuncias) {
                    try {
                        URL url = new URL(denuncia.getUrlFoto());
                        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                        InputStream input = conexao.getInputStream();
                        img = BitmapFactory.decodeStream(input);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                final Bitmap imgAux = img;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(getBaseContext());
                        imageView.setImageBitmap(imgAux);

                        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.constraint_items);
                        cl.addView(imageView);
                    }
                });

            }
        }.start();
    }


}