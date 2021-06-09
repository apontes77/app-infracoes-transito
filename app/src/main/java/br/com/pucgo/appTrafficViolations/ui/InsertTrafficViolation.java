package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.utilities.BinaryBytes;
import br.com.pucgo.appTrafficViolations.utilities.ErrorActivity;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import br.com.pucgo.appTrafficViolations.utilities.SucessActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertTrafficViolation extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private RestApiInterfaceTrafficViolation apiServiceDenuncia;
    private EditText edt_titulo;
    private EditText edt_descricao;
    private ImageView imageView;
    private Button botaoCarregarImagem;
    private Button botaoEnviarInfracao;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_denuncia);
        apiServiceDenuncia = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        edt_titulo = (EditText) findViewById(R.id.edt_title);
        edt_descricao = (EditText) findViewById(R.id.edt_Description);
        imageView = (ImageView) findViewById(R.id.imageView_loadImage);
        botaoCarregarImagem = (Button) findViewById(R.id.button_loadImage);
        botaoEnviarInfracao = (Button) findViewById(R.id.button_sendViolation);

        botaoCarregarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        botaoEnviarInfracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparaDadosParaInsercao();
            }
        });
    }

    private void preparaDadosParaInsercao() {
        String titulo = edt_titulo.getText().toString();
        String descricao = edt_descricao.getText().toString();
        TrafficViolation trafficViolation = new TrafficViolation();
        trafficViolation.setTitle(titulo);
        trafficViolation.setDescription(descricao);
        Gson gson = new Gson();

       // Bitmap imagemBinario = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        byte[] imageBinary = BinaryBytes.getResourceInBytes(this, R.id.imageView_loadImage);
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image/png"), imageBinary);

        Call<TrafficViolation> enviarInfracao = apiServiceDenuncia.insertTrafficViolation(gson.toJson(trafficViolation), requestBodyFile);
        realizaChamadaDeInsercao(enviarInfracao);
    }

    private void realizaChamadaDeInsercao(Call<TrafficViolation> enviarInfracao) {
        enviarInfracao.enqueue(new Callback<TrafficViolation>() {
            @Override
            public void onResponse(Call<TrafficViolation> call, Response<TrafficViolation> response) {
                if (response.code() == 200) {
                    Intent i = new Intent(getApplicationContext(), SucessActivity.class);
                    startActivity(i);
                } else {
                    GenerateToast.createLongToast(getApplicationContext(), "" + response.body());
                    Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<TrafficViolation> call, Throwable t) {
                GenerateToast.createLongToast(getApplicationContext(), "NEM FEZ A REQUISICAO");

                Log.v("tag", t.getMessage());
                Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                startActivity(i);
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
