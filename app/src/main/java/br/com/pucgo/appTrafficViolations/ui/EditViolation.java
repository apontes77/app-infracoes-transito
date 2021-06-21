package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

/**
 * classe que realiza a edição de um item de infração de trânsito
 */
public class EditViolation extends AppCompatActivity {

    private RestApiInterfaceTrafficViolation apiServiceViolation;
    private static final int PICK_FROM_GALLERY = 1;
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
    String fileUri;

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
        saveImage(photoReceived);

        btn_loadImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        });

        btn_sendViolation.setOnClickListener(v -> {
            try {
                makeCallToInsert();
            } catch (IOException e) {
                Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                startActivity(i);
                e.printStackTrace();
            }
        });
    }

    /**
     * executa a chamada a API para atualização.
     * @throws IOException
     */
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
                            if(imageFile.exists()) {
                                imageFile.delete();
                            }

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

    /**
     * retorna uma instância do tipo File para envio da imagem para o Backend.
     * @param name
     * @return
     * @throws IOException
     */
    private File createImageFile(String name) throws IOException {
        return new File(name);
    }

    /**
     * este método serve para permitir a edição de um item de denúncia sem ter que enviar outra imagem.
     * Ou seja, com este, é possível alterar quaisquer campos de texto e manter a imagem como está para
     * realizar a atualização.
     * @param url
     */
    public void saveImage(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/Download");

                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    imageFile = new File(fileUri);
                } catch(IOException e) {
                    Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                    startActivity(i);
                    e.printStackTrace();
                }
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    /**
     * obtém o caminho e nome correto da imagem escolhida no dispositivo do usuário.
     * @param uri
     * @return
     */
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

    /**
     * valida campos vazios com avisos.
     */
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

    /**
     * converte campos o objeto do tipo TrafficViolation para String JSON.
     * @return
     */
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
            Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
            startActivity(i);
            e.printStackTrace();
        }
        Double price = Double.parseDouble(ed_price.getText().toString());

        assert dateNew != null;
        final TrafficViolation violationInsert = buildViolationObject(idToUpdate, title, description, distance, dateNew, price);
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(violationInsert);
    }

    /**
     * cria um objeto do tipo TrafficViolation
     * @param id
     * @param title
     * @param description
     * @param distance
     * @param dateNew
     * @param price
     * @return
     */
    private TrafficViolation buildViolationObject(Integer id, String title, String description, Double distance, Date dateNew, Double price) {
        return TrafficViolation.builder()
                .id(id)
                .title(title)
                .description(description)
                .dateTime(dateNew)
                .violationDistance(distance)
                .proposalAmountTrafficTicket(price)
                .build();
    }

    /**
     * método executado o ato da escolha de uma imagem.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
            Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
            startActivity(i);
            Log.d("IMAGE_ERROR","Alguma exceção ocorreu ao carregar a imagem no ImageView, sendo a mesma: --> "+e.getMessage());
        }
    }
}