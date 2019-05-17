package ua.ulch.nyttest.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ua.ulch.nyttest.MainActivity;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.db.Article;
import ua.ulch.nyttest.fragments.DetailsFragment;
import ua.ulch.nyttest.model.Results;
import ua.ulch.nyttest.program.App;
import ua.ulch.nyttest.util.ReadWriteUtil;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private ArrayList<Results> results;
    private MainActivity activity;

    public ItemListAdapter(ArrayList<Results> results, Activity context) {
        this.results = results;
        this.activity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Results res = results.get(i);
        viewHolder.tvTitle.setText(res.getTitle());
        viewHolder.tvDate.setText(res.getPublished_date());
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.place_holder);
        Glide.with(viewHolder.tvDate.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(res.getMedia()[0].getMediaMetadata()[0].getUrl())
                .into(viewHolder.imageView);
        final String url = res.getUrl();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).showFragment(DetailsFragment.newInstance(url), R.id.container_body, true);
            }
        });
        viewHolder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveToFavorites(res);
            }
        });
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate;
        private ImageView imageView, ivFavorite;

        private ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
            imageView = v.findViewById(R.id.imageView);
            ivFavorite = v.findViewById(R.id.ivFavorite);
        }

    }

    private void saveToFavorites(Results res){

        Article article = new Article(Long.valueOf(res.getId()), res.getTitle()
                , res.getPublished_date(), res.getUrl()
                , res.getMedia()[0].getMediaMetadata()[0].getUrl(), "");
        new DownloadTask(activity).execute(article);
    }

    class DownloadTask extends AsyncTask<Article, Void, Void> {
        private WeakReference<MainActivity> activityReference;
        MainActivity activity;

        DownloadTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected Void doInBackground(final Article... articles) {
            try {
//                String html = Jsoup.connect(articles[0].getUrl()).get().html();
                String filename = articles[0].getId() + ".html";
                Document document = Jsoup.connect(articles[0].getUrl()).get();
                ReadWriteUtil.saveDocument(document, filename);
                articles[0].setHtml(ReadWriteUtil.getUri(filename));
                RequestOptions requestOptions = new RequestOptions().override(200)
                        .downsample(DownsampleStrategy.CENTER_INSIDE)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                final String imagename = articles[0].getImage().substring(articles[0].getImage().lastIndexOf("/") + 1);
                Log.e("filename", imagename);
                Bitmap bitmap = Glide.with(activity)
                        .asBitmap()
                        .load(articles[0].getImage())
                        .apply(requestOptions)
                        .submit()
                        .get();
                ReadWriteUtil.saveImage(bitmap, imagename, activity);
                articles[0].setImage(imagename);
                App.getInstance().getDatabase().articleRoomDao().insert(articles[0]);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}
