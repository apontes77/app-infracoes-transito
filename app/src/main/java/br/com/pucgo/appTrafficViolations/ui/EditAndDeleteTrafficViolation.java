package br.com.pucgo.appTrafficViolations.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
        String distance = bundle.getString("distance");
        String price = bundle.getString("price");

        this.titleEdit = (EditText) findViewById(R.id.txtInputEditText_title_noEdit);
        this.titleEdit.setText(title);
        this.titleEdit.setEnabled(false);

        this.descriptionEdit = (EditText) findViewById(R.id.txtInputEditText_description_noEdit);
        this.descriptionEdit.setText(description);
        this.descriptionEdit.setEnabled(false);

        violationDistance = (EditText) findViewById(R.id.txtInputEditText_violationDistance_noEdit);
        violationDistance.setText(distance);
        violationDistance.setEnabled(false);

        proposalPrice = (EditText) findViewById(R.id.txtInputEditText_proposalPrice_noEdit);
        proposalPrice.setText(price);
        proposalPrice.setEnabled(false);

        photo = (ImageView) findViewById(R.id.imageView_loadImage_noEdit);
        editViolation = (Button) findViewById(R.id.button_edit_violation);
        deleteViolation = (Button) findViewById(R.id.button_delete_violation);


        editViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}