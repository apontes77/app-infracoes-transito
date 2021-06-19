package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditViolation extends AppCompatActivity {

    private RestApiInterfaceTrafficViolation apiServiceViolation;
    private static final int PICK_FROM_GALLERY = 0;
    private EditText ed_title;
    private EditText ed_description;
    private EditText ed_distance;
    private EditText ed_date;
    private EditText ed_price;
    private ImageView iv_imageToSend;
    private Button btn_loadImage;
    private Button btn_sendViolation;
    File imageFile;
    Integer id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_violation);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");
        String photoReceived = bundle.getString("photo");
        Double distance = bundle.getDouble("distance");
        Double price = bundle. getDouble("price");

        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        ed_title =  findViewById(R.id.edt_title);
        ed_description = findViewById(R.id.edt_Description);
        ed_distance = findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        ed_date = findViewById(R.id.txtInputEditText_date_noEdit);
        ed_price = findViewById(R.id.txtInputEditText_proposalPrice_noEdit);

        iv_imageToSend = findViewById(R.id.imageView_loadImage);
        btn_loadImage =  findViewById(R.id.button_loadImage);
        btn_sendViolation = findViewById(R.id.button_sendViolation);

        this.ed_title.setText(title);
        this.ed_date.setText(date);
        this.ed_description.setText(description);
        this.ed_distance.setText(String.valueOf(distance));
        this.ed_price.setText(String.valueOf(price));

        Picasso.get().load(photoReceived).into(iv_imageToSend);

        btn_loadImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        });

        btn_sendViolation.setOnClickListener(v -> {
            try {
                makeCallToInsert();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void makeCallToInsert() throws IOException {

        if (ed_title.getText().toString().isEmpty()
                || ed_description.getText().toString().isEmpty()
                || ed_distance.getText().toString().isEmpty()
                || ed_date.getText().toString().isEmpty()
                || ed_price.getText().toString().isEmpty()) {
            emptyFieldsWarning();
        } else {
            //cria o json da denuncia sem a imagem
            String json = returnsJsonString();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

            Log.v("tent", json);
            apiServiceViolation.updateTrafficViolation(
                    body,
                    RequestBody.create(MediaType.parse("multipart/form-data"), json))
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NotNull Call<Void> call, Response<Void> response) {
                            GenerateToast.createLongToast(EditViolation.this, "Envio realizado com sucesso!");
                            Intent intent = new Intent(EditViolation.this, ListTrafficViolation.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(@NotNull Call<Void> call, Throwable t) {
                            Log.v("LOG", t.getMessage());
                            Intent intent = new Intent(EditViolation.this, ListTrafficViolation.class);
                            startActivity(intent);
                        }
                    });
        }
    }

    private File createImageFile(String name) throws IOException {
        // Create an image file name
        return new File(name);
    }

    public String getRealPathFromURIForGallery(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        assert false;
        cursor.close();
        return uri.getPath();
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
        Integer idToUpdate = id;
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
        final TrafficViolation violationInsert = buildViolationObject(idToUpdate, title, description, distance, dateNew, price);

        return new Gson().toJson(violationInsert);
    }

    private TrafficViolation buildViolationObject(Integer id, String title, String description, Double distance, Date dateNew, Double price) {
        return TrafficViolation.builder()
                .id(id)
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
            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String selectedImagePath = getRealPathFromURIForGallery(selectedImage);
                imageFile = createImageFile(selectedImagePath);
                iv_imageToSend.setImageURI(selectedImage);
            }
        } catch (Exception e) {
            Log.d("IMAGE_ERROR","Alguma exceção ocorreu ao carregar a imagem no ImageView.");
        }
    }
}