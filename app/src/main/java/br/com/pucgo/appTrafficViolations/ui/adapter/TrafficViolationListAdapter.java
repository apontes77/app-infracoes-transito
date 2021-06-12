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

import java.util.List;

import br.com.pucgo.appTrafficViolations.R;
import br.com.pucgo.appTrafficViolations.models.TrafficViolation;

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

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_denuncia_listagem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrafficViolationListAdapter.ViewHolder holder, int position) {

        holder.title.setText(trafficViolations.get(position).getTitle());
        holder.description.setText(trafficViolations.get(position).getDescription());
        Picasso.get().load(trafficViolations.get(position).getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        if(trafficViolations != null){
            return trafficViolations.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        ImageView photo;
        ViolationListener violationListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_violation_title);
            description = itemView.findViewById(R.id.tv_violation_description);
            photo = itemView.findViewById(R.id.iv_violationImage);
            this.violationListener = violationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            violationListener.chooseViolation(getAdapterPosition());
        }
    }

    public interface ViolationListener {
        void chooseViolation(int position);
    }
}
