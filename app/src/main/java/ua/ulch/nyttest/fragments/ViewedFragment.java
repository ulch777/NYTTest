package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.adapter.ItemListAdapter;
import ua.ulch.nyttest.model.Response;
import ua.ulch.nyttest.model.Results;
import ua.ulch.nyttest.networking.NYTApiService;

public class ViewedFragment extends BaseFragment {
    private static final String KEY_VIEWED = "key_viewed";
    private static final String KEY_IS_LOADING = "is_loading";


    @Override
    protected void restore(Bundle savedInstanceState) {
        items = savedInstanceState.getParcelableArrayList(KEY_VIEWED);
        isLoading = savedInstanceState.getBoolean(KEY_IS_LOADING);
    }

    @Override
    protected void getObservable() {
        String str = BASE_URL + TYPE_VIEWED + PERIOD + ".json?api-key=" + API_KEY;
        Log.e("ViewedFragment", str);
        observableRetrofit = NYTApiService.getNYTApi().getItemList(BASE_URL + TYPE_VIEWED + PERIOD + ".json?api-key=" + API_KEY);
    }


    @Override
    public void shoWUnavailable() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_VIEWED, items);
//        outState.putBoolean(Const.KEY_IS_LOADING, isLoading);
    }

}
