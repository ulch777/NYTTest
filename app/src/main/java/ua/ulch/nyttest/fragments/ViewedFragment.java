package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

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
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_VIEWED, items);
    }

}
