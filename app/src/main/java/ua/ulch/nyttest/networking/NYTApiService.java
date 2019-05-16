package ua.ulch.nyttest.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import ua.ulch.nyttest.model.Response;

public class NYTApiService {
    private static final String TAG = NYTApiService.class.getSimpleName();
    private static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    private static final String API_KEY = "mg1ZaNyjOBhYVjGSeEmU8iPppplTji21";
    private static final String PERIOD = "30";
    private static final String TYPE_VIEWED = "viewed/";
    private static final String TYPE_EMAILED = "emailed/";
    private static final String TYPE_SHARED = "shared/";
    private static final String FACEBOOK = "/facebook";

    private static GetItems apiService;
//    private static Observable<Response> observableViewedRetrofit;
//    private static BehaviorSubject<Response> observableItemsList;


    private static Subscription subscription;

    private NYTApiService() {
    }

    public static void init() {
        Log.d(TAG, "init");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(GetItems.class);

//        observableViewedRetrofit = apiService.getItemList(BASE_URL + TYPE_VIEWED + PERIOD + ".json?api-key=" + API_KEY);
    }

//    public static void resetItemsObservable() {
//        observableItemsList = BehaviorSubject.create();
//
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
//        subscription = observableViewedRetrofit.subscribe(new Subscriber<Response>() {
//            @Override
//            public void onCompleted() {
//                Log.e("ItemList", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                observableItemsList.onError(e);
//            }
//
//            @Override
//            public void onNext(Response models) {
//                observableItemsList.onNext(models);
//            }
//        });
//    }
//
//    public static Observable<Response> getItemsObservable() {
//        if (observableItemsList == null) {
//            resetItemsObservable();
//        }
//        return observableItemsList;
//    }
    public static GetItems getNYTApi() {

        return apiService;
    }

}