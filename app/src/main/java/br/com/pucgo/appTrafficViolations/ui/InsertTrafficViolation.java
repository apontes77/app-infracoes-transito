package br.com.pucgo.appTrafficViolations.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.utilities.BinaryBytes;
import br.com.pucgo.appTrafficViolations.utilities.ErrorActivity;
import br.com.pucgo.appTrafficViolations.utilities.SucessActivity;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertTrafficViolation extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int GALLERY_IMAGES = 2;
    private RestApiInterfaceTrafficViolation apiServiceViolation;
    private EditText ed_title;
    private EditText ed_description;
    private EditText ed_distance;
    private EditText ed_price;
    private ImageView iv_imageToSend;
    private Button btn_loadImage;
    private Button btn_sendViolation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_violation);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALLERY_IMAGES);
            }
        }

        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        ed_title = (EditText) findViewById(R.id.edt_title);
        ed_description = (EditText) findViewById(R.id.edt_Description);
        ed_distance = (EditText) findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        ed_price = (EditText) findViewById(R.id.txtInputEditText_proposalPrice_noEdit);

        iv_imageToSend = (ImageView) findViewById(R.id.imageView_loadImage);
        btn_loadImage = (Button) findViewById(R.id.button_loadImage);
        btn_sendViolation = (Button) findViewById(R.id.button_sendViolation);

        btn_loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        btn_sendViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCallToInsert(BinaryBytes.getResourceInBytes(InsertTrafficViolation.this, R.id.imageView_loadImage));
            }
        });
    }

    public void makeCallToInsert(byte[] imageBytes) {

        if (ed_title.getText().toString().isEmpty()
                || ed_description.getText().toString().isEmpty()
                || ed_distance.getText().toString().isEmpty()
                || ed_price.getText().toString().isEmpty()) {
            ed_title.setError("Insira o título");
            ed_title.requestFocus();
            ed_description.setError("Insira a descrição");
            ed_description.requestFocus();
            ed_distance.setError("Insira a distância aproximada!");
            ed_distance.requestFocus();
            ed_price.setError("Insira o valor sugerido para a multa!");
            ed_price.requestFocus();
        } else {
            //cria o json da denuncia sem a imagem
            String json = returnsJsonString();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "file.jpg", requestFile);
            apiServiceViolation.insertTrafficViolation(
                    body,
                    RequestBody.create(MediaType.parse("multipart/form-data"), json))
                    .enqueue(new Callback<TrafficViolation>() {
                        @Override
                        public void onResponse(Call<TrafficViolation> call, Response<TrafficViolation> response) {
                            Intent intent = new Intent(InsertTrafficViolation.this, SucessActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<TrafficViolation> call, Throwable t) {
                            Log.v("LOG", t.getMessage());
                            Intent intent = new Intent(InsertTrafficViolation.this, ErrorActivity.class);
                            startActivity(intent);
                        }
                    });
           }
    }

    private String returnsJsonString() {
        String title = ed_title.getText().toString();
        String description = ed_description.getText().toString();
        Double distance = Double.parseDouble(ed_distance.getText().toString());
        Double price = Double.parseDouble(ed_price.getText().toString());

        final TrafficViolation violationInsert = TrafficViolation.builder()
                .title(title)
                .description(description)
                .violationDistance(distance)
                .proposalAmountTrafficTicket(price)
                .build();

        String json = new Gson().toJson(violationInsert);
        return json;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri imageContent = data.getData();
            iv_imageToSend.setImageURI(imageContent);
        }
    }

}