package br.com.pucgo.appTrafficViolations.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;

public class TrafficViolationListAdapter extends RecyclerView.Adapter<TrafficViolationListAdapter.ViewHolder> {
    private Context context;
    private denunciaListener mDenunciaListener;
    private List<TrafficViolation> trafficViolations = new ArrayList<>();
    public TrafficViolationListAdapter(Context context, List<TrafficViolation> trafficViolations, denunciaListener mDenunciaListener) {
        this.context = context;
        this.trafficViolations = trafficViolations;
        this.mDenunciaListener = mDenunciaListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_denuncia_listagem, parent, false);
        return new ViewHolder(view, mDenunciaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrafficViolationListAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(trafficViolations.get(position).getTitle());
        holder.descricao.setText(trafficViolations.get(position).getDescription());
      Picasso
            .get()
            .load("https://app-infracoes-transito.s3.us-east-2.amazonaws.com/meliante.jpg")
            .into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return trafficViolations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titulo;
        TextView descricao;
        ImageView foto;
        denunciaListener denunciaListener;

        public ViewHolder(@NonNull View itemView, denunciaListener denunciaListener) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textView_titulo);
            descricao = itemView.findViewById(R.id.textView_descricao);
            foto = itemView.findViewById(R.id.imageView_1);
            this.denunciaListener = denunciaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            denunciaListener.selecionaDenuncia(getAdapterPosition());
        }
    }

    public interface denunciaListener {
        void selecionaDenuncia(int position);
    }
}
