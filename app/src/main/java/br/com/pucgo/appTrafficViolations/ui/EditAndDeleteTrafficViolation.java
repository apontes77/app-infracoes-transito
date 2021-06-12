package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.pucgo.appTrafficViolations.R;

public class EditAndDeleteTrafficViolation extends AppCompatActivity {

    private EditText titleEdit;
    private EditText descriptionEdit;
    private EditText violationDistance;
    private EditText proposalPrice;
    private ImageView photo;
    private Button editViolation;
    private Button deleteViolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_excluir_denuncia);
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


            }
        });

    }
}