package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.ui.adapter.TrafficViolationListAdapter;
import br.com.pucgo.appTrafficViolations.utilities.GenerateToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTrafficViolation extends AppCompatActivity implements TrafficViolationListAdapter.ViolationListener {

    private RecyclerView recyclerView;
    private RestApiInterfaceTrafficViolation apiServiceViolation;
    private TextView title;
    private TextView description;
    private FloatingActionButton fab;
    private ImageView image;
    private List<TrafficViolation> trafficViolations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_violations);
        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listViolations);
        title = (TextView) findViewById(R.id.textView_title);
        description = (TextView) findViewById(R.id.textView_description);
        image = (ImageView) findViewById(R.id.imageView_violationInfraction);

        apiServiceViolation.listTrafficViolations().enqueue(new Callback<List<TrafficViolation>>() {
            @Override
            public void onResponse(Call<List<TrafficViolation>> call, Response<List<TrafficViolation>> response) {
                List<TrafficViolation> body = response.body();
                showViolations(body);
            }
            @Override
            public void onFailure(Call<List<TrafficViolation>> call, Throwable t) {
                Log.v("ADA", t.getMessage());
                GenerateToast.createLongToast(getApplicationContext(), "Houve algum problema na busca das infrações registradas!");
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityInsertViolation());
    }

    private void showViolations(List<TrafficViolation> violations) {
        TrafficViolationListAdapter adapter = new TrafficViolationListAdapter(getApplicationContext(), violations, ListTrafficViolation.this::chooseViolation);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @NotNull
    private View.OnClickListener initiateActivityInsertViolation() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InsertTrafficViolation.class);
                startActivity(i);
            }
        };
    }

    @Override
    public void chooseViolation(int position) {
        trafficViolations.get(position);
        Intent intent = new Intent(this, EditAndDeleteTrafficViolation.class);
        Bundle bundle = new Bundle();
        bundle.putString("titulo", trafficViolations.get(position).getTitle());
        bundle.putString("descricao", trafficViolations.get(position).getDescription());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}