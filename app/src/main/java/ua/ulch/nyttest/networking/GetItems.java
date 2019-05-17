package ua.ulch.nyttest.networking;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import ua.ulch.nyttest.model.Response;

public interface GetItems {
    @GET
    Observable<Response> getItemList(@Url String url);
}
