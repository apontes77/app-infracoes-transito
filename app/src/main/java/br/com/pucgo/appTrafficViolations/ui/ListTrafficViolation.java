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
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    private TrafficViolationListAdapter mAdapter;

    private FloatingActionButton fab;
    private RestApiInterfaceTrafficViolation apiServiceDenuncia;


    List<TrafficViolation> violations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_violations);
        violations = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listViolations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ListTrafficViolation.this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TrafficViolationListAdapter(getApplicationContext(), violations);
        recyclerView.setAdapter(mAdapter);

        apiServiceDenuncia = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);
        Call<List<TrafficViolation>> listCall = apiServiceDenuncia.listTrafficViolations();

        listCall.enqueue(new Callback<List<TrafficViolation>>() {
            @Override
            public void onResponse(Call<List<TrafficViolation>> call, Response<List<TrafficViolation>> response) {
                violations = response.body();
                Log.d("TAG", ""+violations);
                mAdapter.setTrafficViolations(violations);
            }

            @Override
            public void onFailure(Call<List<TrafficViolation>> call, Throwable t) {
                Log.d("TAG", ""+t.toString());
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityInsertViolation());
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
        violations.get(position);
        Intent intent = new Intent(this, EditAndDeleteTrafficViolation.class);
        Bundle bundle = new Bundle();
        bundle.putString("titulo", violations.get(position).getTitle());
        bundle.putString("descricao", violations.get(position).getDescription());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}