package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import ua.ulch.nyttest.networking.NYTApiService;

public class EmailedFragment extends BaseFragment {
    private static final String KEY_EMAILED = "key_emailed";


    @Override
    protected void restore(Bundle savedInstanceState) {
        items = savedInstanceState.getParcelableArrayList(KEY_EMAILED);
    }

    @Override
    protected void getObservable() {
        String str = BASE_URL + TYPE_EMAILED + PERIOD + ".json?api-key=" + API_KEY;
        Log.e("EmailedFragment", str);
        observableRetrofit = NYTApiService.getNYTApi().getItemList(BASE_URL + TYPE_EMAILED + PERIOD + ".json?api-key=" + API_KEY);
    }


    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_EMAILED, items);
    }

}
