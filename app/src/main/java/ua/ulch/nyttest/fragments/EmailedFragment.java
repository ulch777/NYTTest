package ua.ulch.nyttest.fragments;
import ua.ulch.nyttest.networking.NYTApiService;

public class EmailedFragment extends BaseFragment {

    @Override
    protected void getObservable() {
        observableRetrofit = NYTApiService.getNYTApi()
                .getItemList(BASE_URL + TYPE_EMAILED + PERIOD + ".json?api-key=" + API_KEY);
    }

}
