package ua.ulch.nyttest.fragments;
import ua.ulch.nyttest.networking.NYTApiService;

public class SharedFragment extends BaseFragment {

    @Override
    protected void getObservable() {
        observableRetrofit = NYTApiService.getNYTApi()
                .getItemList(BASE_URL + TYPE_SHARED + PERIOD + FACEBOOK+ ".json?api-key=" + API_KEY);
    }


}
