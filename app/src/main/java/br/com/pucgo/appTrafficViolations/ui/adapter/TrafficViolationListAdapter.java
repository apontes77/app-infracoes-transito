package br.com.pucgo.appTrafficViolations.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.ui.EditAndDeleteTrafficViolation;

/**
 * Classe que estende outra classe Adapter, a fim de prover as configurações necessárias para uso correto
 * do componente gráfico RecyclerView.
 */
public class TrafficViolationListAdapter extends RecyclerView.Adapter<TrafficViolationListAdapter.ViewHolder> {
     Context context;
     List<TrafficViolation> trafficViolations;

    public TrafficViolationListAdapter(Context context, List<TrafficViolation> trafficViolations) {
        this.context = context;
        this.trafficViolations = trafficViolations;
    }

    public void setTrafficViolations(List<TrafficViolation> trafficViolations) {
        this.trafficViolations = trafficViolations;
        notifyDataSetChanged();
    }

    /**
     *
     * @param parent - é o componente do tipo View a ser carregado dentro do RecyclerView
     * @param viewType - é o viewType.
     * @return - retorna um objeto do tipo ViewHolder
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_violation, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     *
     * @param holder - é o objeto da classe ViewHolder, responsável por manipular cada unidade disponível no RecyclerView.
     * @param position - é a posição obtida na estrutura de dados que contém os dados a serem manipulados no viewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull TrafficViolationListAdapter.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf((trafficViolations.get(position).getId())));
        holder.title.setText(trafficViolations.get(position).getTitle());
        holder.description.setText(trafficViolations.get(position).getDescription());
        holder.distance.setText(String.valueOf(trafficViolations.get(position).getViolationDistance()).concat(" m"));
        holder.price.setText("R$ ".concat(String.valueOf(trafficViolations.get(position).getProposalAmountTrafficTicket())));
        Picasso.get().load(trafficViolations.get(position).getPhoto()).into(holder.photo);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // envia os dados para tela de escolha de edição ou exclusão
                Intent intent = new Intent(context, EditAndDeleteTrafficViolation.class);
                intent.putExtra("id", trafficViolations.get(position).getId());
                intent.putExtra("title", trafficViolations.get(position).getTitle());
                intent.putExtra("date", formatter.format(trafficViolations.get(position).getDateTime()));
                intent.putExtra("description", trafficViolations.get(position).getDescription());
                intent.putExtra("photo",trafficViolations.get(position).getPhoto());
                intent.putExtra("distance", trafficViolations.get(position).getViolationDistance());
                intent.putExtra("price", trafficViolations.get(position).getProposalAmountTrafficTicket());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    /**
     *
     * @return  - retorna a quantidade de itens carregados no RecyclerView
     */

    @Override
    public int getItemCount() {
        if(trafficViolations != null){
            return trafficViolations.size();
        }
        return 0;
    }

    /**
     * classe responsável por ser o modelo de cada item individual do RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView title;
        TextView description;
        TextView distance;
        TextView price;
        ImageView photo;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            title = itemView.findViewById(R.id.tv_violation_title);
            description = itemView.findViewById(R.id.tv_violation_description);
            photo = itemView.findViewById(R.id.iv_violationImage);
            distance = itemView.findViewById(R.id.tv_distance);
            price = itemView.findViewById(R.id.tv_price);
            constraintLayout = itemView.findViewById(R.id.oneViolationLayout);
        }
    }
}
