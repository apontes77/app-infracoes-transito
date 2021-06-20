package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.utilities.ErrorActivity;
import br.com.pucgo.appTrafficViolations.utilities.SucessActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Esta é a Activity que oferece a opção de editar ou excluir uma determinada denúncia de infração de trânsito.
 */
public class EditAndDeleteTrafficViolation extends AppCompatActivity {

    private EditText titleEdit;
    private EditText descriptionEdit;
    private EditText violationDistance;
    private EditText proposalPrice;
    private EditText date;
    private ImageView photo;
    private Button editViolation;
    private Button deleteViolation;
    TrafficViolation tv;
    Integer id = null;
    private RestApiInterfaceTrafficViolation apiServiceViolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_violation);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");
        String photoReceived = bundle.getString("photo");
        Double distance = bundle.getDouble("distance");
        Double price = bundle. getDouble("price");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date parse = new Date();
        try{
             parse = sdf.parse(date);
        } catch (ParseException e) {
            Log.v("DATE", e.getMessage());
        }

        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        this.titleEdit = (EditText) findViewById(R.id.txtInputEditText_title_noEdit);
        this.titleEdit.setText(title);
        this.titleEdit.setEnabled(false);

        this.descriptionEdit = (EditText) findViewById(R.id.txtInputEditText_description_noEdit);
        this.descriptionEdit.setText(description);
        this.descriptionEdit.setEnabled(false);

        violationDistance = (EditText) findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        violationDistance.setText(String.valueOf(distance));
        violationDistance.setEnabled(false);

        this.date = (EditText) findViewById(R.id.txtInputEditText_date_noEdit);
        this.date.setText(date);
        this.date.setEnabled(false);

        proposalPrice = (EditText) findViewById(R.id.txtInputEditText_proposalPrice_noEdit);
        proposalPrice.setText(String.valueOf(price));
        proposalPrice.setEnabled(false);

        photo = (ImageView) findViewById(R.id.imageView_loadImage_noEdit);
        Picasso.get().load(photoReceived).into(photo);
        editViolation = (Button) findViewById(R.id.button_edit_violation);
        deleteViolation = (Button) findViewById(R.id.button_delete_violation);

        tv = new TrafficViolation(id, title, description, photoReceived, parse, distance, price);

        /**
         * método que envia os dados de uma denúncia para tela de edição
         */
        editViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAndDeleteTrafficViolation.this, EditViolation.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("description", description);
                intent.putExtra("photo",photoReceived);
                intent.putExtra("distance", distance);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        /**
         * método que executa a exclusão de um item de denúncia.
         */

        deleteViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAndDeleteTrafficViolation.this);
                builder.setTitle("Tem certeza?");
                builder.setMessage("Esta ação é irreversível...");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("OBJ RECEIVED", new Gson().toJson(tv));
                        Log.d("ID RECEIVED:", "---> "+tv.getId());
                        Integer idToDelete = tv.getId();

                        final Call<Void> voidCall = apiServiceViolation.deleteTrafficViolation(idToDelete);

                        voidCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intent = new Intent(EditAndDeleteTrafficViolation.this, SucessActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("error", t.getMessage());
                                Intent intent = new Intent(EditAndDeleteTrafficViolation.this, ErrorActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }
}