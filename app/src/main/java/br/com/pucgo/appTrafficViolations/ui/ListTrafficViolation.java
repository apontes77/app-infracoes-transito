package br.com.pucgo.appTrafficViolations.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import br.com.pucgo.appTrafficViolations.ui.adapter.TrafficViolationListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTrafficViolation extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private TrafficViolationListAdapter mAdapter;

    private FloatingActionButton fab;
    private RestApiInterfaceTrafficViolation apiServiceViolation;

    List<TrafficViolation> violations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_violations);
        violations = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_listViolations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ListTrafficViolation.this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TrafficViolationListAdapter(getApplicationContext(), violations);
        recyclerView.setAdapter(mAdapter);

        apiServiceViolation = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);
        Call<List<TrafficViolation>> listCall = apiServiceViolation.listTrafficViolations();

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

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityInsertViolation());
    }

    @NotNull
    private View.OnClickListener initiateActivityInsertViolation() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListTrafficViolation.this, InsertTrafficViolation.class);
                startActivity(i);
            }
        };
    }
}