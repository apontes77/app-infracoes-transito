package br.com.pucgo.appInfracoes.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.appInfracoes.R;
import br.com.pucgo.appInfracoes.modelos.Denuncia;

public class ListaDenunciasAdapter extends RecyclerView.Adapter<ListaDenunciasAdapter.ViewHolder> {
    Context context;
    private OnNoteListener mOnNoteListener;
    List<Denuncia> denuncias = new ArrayList<>();
    public ListaDenunciasAdapter(Context context, List<Denuncia> denuncias, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.denuncias = denuncias;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_denuncia_listagem, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListaDenunciasAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(denuncias.get(position).getTitulo());
        holder.descricao.setText(denuncias.get(position).getDescricao());
      Picasso
            .get()
            .load("https://app-infracoes-transito.s3.us-east-2.amazonaws.com/meliante.jpg")
            .into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titulo;
        TextView descricao;
        ImageView foto;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textView_titulo);
            descricao = itemView.findViewById(R.id.textView_descricao);
            foto = itemView.findViewById(R.id.imageView_1);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
