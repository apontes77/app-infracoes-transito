package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
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
    private ImageView photo;
    private Button editViolation;
    private Button deleteViolation;
    private RestApiInterfaceTrafficViolation apiServiceViolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_violation);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String photoReceived = bundle.getString("photo");
        Double distance = bundle.getDouble("distance");
        Double price = bundle. getDouble("price");

        this.titleEdit = (EditText) findViewById(R.id.txtInputEditText_title_noEdit);
        this.titleEdit.setText(title);
        this.titleEdit.setEnabled(false);

        this.descriptionEdit = (EditText) findViewById(R.id.txtInputEditText_description_noEdit);
        this.descriptionEdit.setText(description);
        this.descriptionEdit.setEnabled(false);

        violationDistance = (EditText) findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        violationDistance.setText(String.valueOf(distance));
        violationDistance.setEnabled(false);

        proposalPrice = (EditText) findViewById(R.id.txtInputEditText_proposalPrice_noEdit);
        proposalPrice.setText(String.valueOf(price));
        proposalPrice.setEnabled(false);

        photo = (ImageView) findViewById(R.id.imageView_loadImage_noEdit);
        Picasso.get().load(photoReceived).into(photo);
        editViolation = (Button) findViewById(R.id.button_edit_violation);
        deleteViolation = (Button) findViewById(R.id.button_delete_violation);


        editViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAndDeleteTrafficViolation.this, InsertTrafficViolation.class);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("photo",photoReceived);
                intent.putExtra("distance", distance);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        deleteViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAndDeleteTrafficViolation.this);
                builder.setTitle("Tem certeza?");
                builder.setMessage("Esta ação é irreversível...");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        apiServiceViolation.deleteTrafficViolation(1).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }
}