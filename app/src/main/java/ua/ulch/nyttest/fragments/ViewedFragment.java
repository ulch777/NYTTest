package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.adapter.ItemListAdapter;
import ua.ulch.nyttest.model.Response;
import ua.ulch.nyttest.model.Results;
import ua.ulch.nyttest.networking.RetrofitSingleton;

public class ViewedFragment extends BaseFragment {
    private static final String TAG = ViewedFragment.class.getSimpleName();
    private static final String KEY_VIEWED = "key_viewed";
    private ArrayList<Results> items = new ArrayList<>();
    ItemListAdapter itemAdapter;

    @Override
    protected void initRes() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemListAdapter(items);
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    protected void restore(Bundle savedInstanceState) {
        items = savedInstanceState.getParcelableArrayList(KEY_VIEWED);
    }

    @Override
    protected void getItemsList() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RetrofitSingleton.getItemsObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError", e);
//                    isLoading = false;
                        if (isAdded()) {
//                        showLoadingIndicator(false);
                            Snackbar.make(recyclerView, R.string.connection_error, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.try_again, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RetrofitSingleton.resetItemsObservable();
//                                        showLoadingIndicator(true);
                                            getItemsList();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onNext(Response newModels) {
                        Log.d(TAG, "onNext: " + newModels.getNum_results());
                        int prevSize = items.size();
//                    isLoading = false;
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeRemoved(0, prevSize);
                        }
                        items.clear();
                        items.addAll(Arrays.asList(newModels.getResults()));
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeInserted(0, items.size());
//                        showLoadingIndicator(false);
                        }
                    }
                });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
