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
    private ViolationListener mViolationListener;
    private List<TrafficViolation> trafficViolations = new ArrayList<>();
    public TrafficViolationListAdapter(Context context, List<TrafficViolation> trafficViolations, ViolationListener mViolationListener) {
        this.context = context;
        this.trafficViolations = trafficViolations;
        this.mViolationListener = mViolationListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_denuncia_listagem, parent, false);
        return new ViewHolder(view, mViolationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrafficViolationListAdapter.ViewHolder holder, int position) {
        TrafficViolation violation = trafficViolations.get(position);
        holder.title.setText(violation.getTitle());
        holder.description.setText(violation.getDescription());
        holder.dateOfViolation.setText(violation.getDateOfOccurrenceInfraction().toString());
        Picasso.get()
                .load(violation.getPhoto())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return trafficViolations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView dateOfViolation;
        ImageView photo;
        ViolationListener violationListener;

        public ViewHolder(@NonNull View itemView, ViolationListener violationListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            description = itemView.findViewById(R.id.textView_description);
            photo = itemView.findViewById(R.id.imageView_violationInfraction);
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
