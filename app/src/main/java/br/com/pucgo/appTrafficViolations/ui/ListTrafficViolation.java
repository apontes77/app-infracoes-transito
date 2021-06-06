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

public class ListTrafficViolation extends AppCompatActivity implements TrafficViolationListAdapter.denunciaListener {

    private RecyclerView recyclerView;
    private RestApiInterfaceTrafficViolation apiServiceDenuncia;
    private TextView tituloDenuncia;
    private TextView descricaoDenuncia;
    private FloatingActionButton fab;
    private ImageView imagem;
    private List<TrafficViolation> trafficViolations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_denuncias);
        apiServiceDenuncia = RestApiClient.getClient().create(RestApiInterfaceTrafficViolation.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listarDenuncias);
        tituloDenuncia = (TextView) findViewById(R.id.textView_titulo);
        descricaoDenuncia = (TextView) findViewById(R.id.textView_descricao);
        imagem = (ImageView) findViewById(R.id.imageView_1);

        Call<List<TrafficViolation>> listCall = apiServiceDenuncia.listTrafficViolations();
        listCall.enqueue(new Callback<List<TrafficViolation>>() {
            @Override
            public void onResponse(Call<List<TrafficViolation>> call, Response<List<TrafficViolation>> response) {
                List<TrafficViolation> denunciasList = response.body();


                Log.v("est", new Gson().toJson(response.body()));
                TrafficViolationListAdapter adapter = new TrafficViolationListAdapter(getApplicationContext(), denunciasList, ListTrafficViolation.this::selecionaDenuncia);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<TrafficViolation>> call, Throwable t) {
                GenerateToast.createLongToast(getApplicationContext(), "Algum erro ocorreu na busca das infrações");
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(initiateActivityManipularDenuncia());

//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//        denuncias.add(new Denuncia("RACHADOR",
//                "rachador na av portugal"));
//
//
//        ListaDenunciasAdapter adapter = new ListaDenunciasAdapter(this, denuncias, this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @NotNull
    private View.OnClickListener initiateActivityManipularDenuncia() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InsertTrafficViolation.class);
                startActivity(i);
            }
        };
    }

    @Override
    public void selecionaDenuncia(int position) {
        trafficViolations.get(position);
        Intent intent = new Intent(this, EditAndDeleteTrafficViolation.class);
        Bundle bundle = new Bundle();
        bundle.putString("titulo", trafficViolations.get(position).getTitle());
        bundle.putString("descricao", trafficViolations.get(position).getDescription());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}