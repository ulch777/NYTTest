package ua.ulch.nyttest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ua.ulch.nyttest.MainActivity;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.db.Article;
import ua.ulch.nyttest.fragments.FavoriteDetailsFragment;
import ua.ulch.nyttest.util.ReadWriteUtil;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    private List<Article> articles;

    public FavoritesListAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_favorite_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(articles.get(i).getTitle());
        viewHolder.tvDate.setText(articles.get(i).getPublishedDate());
        Glide.with(viewHolder.tvDate.getContext()).asBitmap()
                .load(ReadWriteUtil.getUri(articles.get(i).getImage()))
                .error(R.drawable.place_holder)
                .into(viewHolder.imageView);
        final String url = articles.get(i).getHtml();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext())
                        .showFragment(FavoriteDetailsFragment.newInstance(url)
                                , R.id.container_body, true);
            }
        });
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateList(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
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
