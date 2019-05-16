package ua.ulch.nyttest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ua.ulch.nyttest.R;
import ua.ulch.nyttest.model.Results;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private ArrayList<Results> results;

    public ItemListAdapter(ArrayList<Results> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(results.get(i).getTitle());
        viewHolder.tvDate.setText(results.get(i).getPublished_date());
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate;

        private ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
        }

    }
}
