package br.com.pucgo.appTrafficViolations.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.utilities.ErrorActivity;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class InsertTrafficViolation extends AppCompatActivity {

    private RestApiInterfaceTrafficViolation apiServiceViolation;
    private EditText ed_title;
    private EditText ed_description;
    private EditText ed_distance;
    private EditText ed_date;
    private EditText ed_price;
    private ImageView iv_imageToSend;
    private Button btn_loadImage;
    private Button btn_sendViolation;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_violation);
        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        ed_title =  findViewById(R.id.edt_title);
        ed_description = findViewById(R.id.edt_Description);
        ed_distance = findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        ed_date = findViewById(R.id.txtInputEditText_date_noEdit);
        ed_price = findViewById(R.id.txtInputEditText_proposalPrice_noEdit);
//        Drawable drawable = getResources().getDrawable(R.drawable.exemplo);
//        bitmap = ((BitmapDrawable) drawable).getBitmap();

        iv_imageToSend = findViewById(R.id.imageView_loadImage);
        btn_loadImage =  findViewById(R.id.button_loadImage);
        btn_sendViolation = findViewById(R.id.button_sendViolation);

        btn_loadImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(galleryIntent, 0);
        });

        btn_sendViolation.setOnClickListener(v -> {
            makeCallToInsert();
        });
    }


    public void makeCallToInsert() {

        if (ed_title.getText().toString().isEmpty()
                || ed_description.getText().toString().isEmpty()
                || ed_distance.getText().toString().isEmpty()
                || ed_date.getText().toString().isEmpty()
                || ed_price.getText().toString().isEmpty()) {
            emptyFieldsWarning();
        } else {
            //cria o json da denuncia sem a imagem
            String json = returnsJsonString();
            ContextWrapper cw = new ContextWrapper(getApplicationContext());

            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, "UniqueFileName1" + ".jpg");
            if (!file.exists()) {
                Log.d("path", file.toString());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    iv_imageToSend.setImageBitmap(bitmap);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            Log.v("tent", json);
            apiServiceViolation.insertTrafficViolation(
                    body,
                    RequestBody.create(MediaType.parse("multipart/form-data"), json))
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NotNull Call<Void> call, Response<Void> response) {
                            GenerateToast.createLongToast(InsertTrafficViolation.this, "Envio realizado com sucesso!");
                            Intent intent = new Intent(InsertTrafficViolation.this, ListTrafficViolation.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(@NotNull Call<Void> call, Throwable t) {
                            Log.v("LOG", t.getMessage());
                            Intent intent = new Intent(InsertTrafficViolation.this, ListTrafficViolation.class);
                            startActivity(intent);
                        }
                    });
        }
    }

    private void emptyFieldsWarning() {
        ed_title.setError("Insira o título");
        ed_title.requestFocus();
        ed_description.setError("Insira a descrição");
        ed_description.requestFocus();
        ed_distance.setError("Insira a distância aproximada!");
        ed_distance.requestFocus();
        ed_date.requestFocus();
        ed_date.setError("Insira a data do ocorrido");
        ed_price.setError("Insira o valor sugerido para a multa!");
        ed_price.requestFocus();
    }

    private String returnsJsonString() {
        String title = ed_title.getText().toString();
        String description = ed_description.getText().toString();
        Double distance = Double.parseDouble(ed_distance.getText().toString());
        String date = ed_date.getText().toString();
        Date dateNew = null;
        try{
            dateNew = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Double price = Double.parseDouble(ed_price.getText().toString());

        assert dateNew != null;
        final TrafficViolation violationInsert = buildViolationObject(title, description, distance, dateNew, price);

        return new Gson().toJson(violationInsert);
    }

    private TrafficViolation buildViolationObject(String title, String description, Double distance, Date dateNew, Double price) {
        return TrafficViolation.builder()
                .title(title)
                .description(description)
                .dateTime(dateNew.toString())
                .violationDistance(distance)
                .proposalAmountTrafficTicket(price)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                iv_imageToSend.setImageURI(selectedImage);
            }
        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }
    }
}