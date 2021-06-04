package br.com.pucgo.appInfracoes.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.pucgo.appInfracoes.R;

public class InserirDenuncia extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView imageView;
    Button botaoCarregarImagem;
    Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_denuncia);

        imageView = (ImageView) findViewById(R.id.imageView_carregarImagem);
        botaoCarregarImagem = (Button) findViewById(R.id.button_carregarImagem);

        botaoCarregarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}
