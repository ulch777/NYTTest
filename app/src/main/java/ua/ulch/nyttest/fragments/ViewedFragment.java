package ua.ulch.nyttest.fragments;

import ua.ulch.nyttest.networking.NYTApiService;

public class ViewedFragment extends BaseFragment {

    @Override
    protected void getObservable() {
        observableRetrofit = NYTApiService.getNYTApi()
                .getItemList(BASE_URL + TYPE_VIEWED + PERIOD + ".json?api-key=" + API_KEY);
    }

}
