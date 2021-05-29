package br.com.pucgo.appInfracoes.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Denuncia;

public class ListaDenunciasAdapter extends RecyclerView.Adapter<ListaDenunciasAdapter.ViewHolder> {
    Context context;

    List<Denuncia> denuncias = new ArrayList<>();
    public ListaDenunciasAdapter(Context context, List<Denuncia> denuncias) {
        this.context = context;
        this.denuncias = denuncias;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_denuncia_listagem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListaDenunciasAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(denuncias.get(position).getTitulo());
        holder.descricao.setText(denuncias.get(position).getDescricao());

    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descricao;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textView_titulo);
            descricao = itemView.findViewById(R.id.textView_descricao);
            foto = itemView.findViewById(R.id.imageView_1);

        }
    }
}
