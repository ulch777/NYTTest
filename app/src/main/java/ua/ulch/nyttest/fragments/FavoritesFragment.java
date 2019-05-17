package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.adapter.FavoritesListAdapter;
import ua.ulch.nyttest.db.Article;
import ua.ulch.nyttest.program.App;

public class FavoritesFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    protected List<Article> items = new ArrayList<>();
    protected FavoritesListAdapter adapter;
    private Disposable subscription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, rootView);
        initRes();
        return rootView;
    }

    protected void initRes() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesListAdapter(items);
        recyclerView.setAdapter(adapter);
        subscription = App.getInstance().getDatabase().articleRoomDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(List<Article> articles) throws Exception {
                        Log.e("accept", articles.toString());
                        adapter.updateList(articles);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription!= null && !subscription.isDisposed())
        subscription.dispose();
    }
}
