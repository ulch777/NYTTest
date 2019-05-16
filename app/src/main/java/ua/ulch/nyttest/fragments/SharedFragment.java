package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.networking.NYTApiService;

public class SharedFragment extends BaseFragment {
    private static final String KEY_SHARED = "key_shared";


    @Override
    protected void restore(Bundle savedInstanceState) {
        items = savedInstanceState.getParcelableArrayList(KEY_SHARED);
    }

    @Override
    protected void getObservable() {

        observableRetrofit = NYTApiService.getNYTApi().getItemList(BASE_URL + TYPE_SHARED + PERIOD + FACEBOOK+ ".json?api-key=" + API_KEY);
    }


    @Override
    public void shoWUnavailable() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SHARED, items);
//        outState.putBoolean(Const.KEY_IS_LOADING, isLoading);
    }

}
