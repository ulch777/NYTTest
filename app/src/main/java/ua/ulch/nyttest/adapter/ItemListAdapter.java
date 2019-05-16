package ua.ulch.nyttest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ua.ulch.nyttest.MainActivity;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.fragments.DetailsFragment;
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
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.place_holder);
        Glide.with(viewHolder.tvDate.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(results.get(i).getMedia()[0].getMediaMetadata()[0].getUrl())
                .into(viewHolder.imageView);
        final String url = results.get(i).getUrl();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_body, DetailsFragment.newInstance(url))
                        .commit();
            }
        });
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate;
        private ImageView imageView;

        private ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
            imageView = v.findViewById(R.id.imageView);
        }

    }
}
