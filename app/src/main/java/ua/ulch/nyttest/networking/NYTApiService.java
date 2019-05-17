package ua.ulch.nyttest.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NYTApiService {
    private static final String TAG = NYTApiService.class.getSimpleName();
    private static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";

    private static GetItems apiService;

    private NYTApiService() {
    }

    public static void init() {
        Log.d(TAG, "init");


        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(GetItems.class);

    }

    public static GetItems getNYTApi() {

        return apiService;
    }

}