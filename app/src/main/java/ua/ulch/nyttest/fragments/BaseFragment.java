package ua.ulch.nyttest.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.adapter.ItemListAdapter;
import ua.ulch.nyttest.model.Response;
import ua.ulch.nyttest.model.Results;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    public static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    public static final String API_KEY = "mg1ZaNyjOBhYVjGSeEmU8iPppplTji21";
    public static final String PERIOD = "30";
    public static final String TYPE_VIEWED = "viewed/";
    public static final String TYPE_EMAILED = "emailed/";
    public static final String TYPE_SHARED = "shared/";
    public static final String FACEBOOK = "/facebook";
    protected Disposable subscription;
    protected Observable<Response> observableRetrofit;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    protected ArrayList<Results> items = new ArrayList<>();
    protected ItemListAdapter itemAdapter;
    @BindView(R.id.loading_indicator)
    ImageView loadingIndicator;
    protected boolean isLoading;

    protected abstract void getObservable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, rootView);
        getObservable();
        initRes();
        if (items.size() == 0 || isLoading) {
            showLoadingIndicator(true);
            getItemsList();
        }
        return rootView;
    }

    protected void initRes() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemListAdapter(items, getActivity() );
        recyclerView.setAdapter(itemAdapter);
    }

    protected void getItemsList() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }

         observableRetrofit.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {
                        int prevSize = items.size();
                        isLoading = false;
                        runLayoutAnimation(recyclerView);
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeRemoved(0, prevSize);
                        }
                        items.clear();
                        items.addAll(Arrays.asList(response.getResults()));
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeInserted(0, items.size());
                            showLoadingIndicator(false);
                            runLayoutAnimation(recyclerView);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError", e);
                        isLoading = false;
                        if (isAdded()) {
                            showLoadingIndicator(false);
                            Snackbar.make(recyclerView, R.string.connection_error, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.try_again, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            resetItemsObservable();
                                            showLoadingIndicator(true);
                                            getItemsList();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onCompleted");

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        LayoutAnimationController controller = AnimationUtils
                .loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void showLoadingIndicator(boolean show) {

        isLoading = show;
        if (isLoading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                    .rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingIndicator.animate().
                            setInterpolator(new AccelerateDecelerateInterpolator())
                            .rotationBy(360).setDuration(500).setListener(this);
                }
            });
        } else {
            loadingIndicator.animate().cancel();
            loadingIndicator.setVisibility(View.GONE);
        }
    }

}


