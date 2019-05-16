package ua.ulch.nyttest.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
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
    protected Subscription subscription;
    protected Observable<Response> observableRetrofit;
    protected BehaviorSubject<Response> observableItemsList;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    protected ArrayList<Results> items = new ArrayList<>();
    protected ItemListAdapter itemAdapter;
    @BindView(R.id.loading_indicator)
    ImageView loadingIndicator;
    protected boolean isLoading;


    /**
     * this method used to Set root layout of the fragment
     */
//    protected abstract int getLayoutRes();

    /**
     * this method used to initiate all the resources in the Fragment
     */

    protected abstract void restore(Bundle bundle);

    protected abstract void getObservable();

    public abstract void shoWUnavailable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        if (savedInstanceState != null)
            restore(savedInstanceState);
        ButterKnife.bind(this, rootView);
        getObservable();
        initRes();
        if (items.size() == 0 || isLoading) {
            showLoadingIndicator(true);
            getItemsList();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    protected void initRes() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemListAdapter(items);
        recyclerView.setAdapter(itemAdapter);
    }

    protected void getItemsList() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = getItemsObservable().
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
                        isLoading = false;
                        if (isAdded()) {
                            showLoadingIndicator(false);
                            Snackbar.make(recyclerView, R.string.connection_error, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.try_again, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            resetItemsObservable();
                                            showLoadingIndicator(true);
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
                        isLoading = false;
                        runLayoutAnimation(recyclerView);
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeRemoved(0, prevSize);
                        }
                        items.clear();
                        items.addAll(Arrays.asList(newModels.getResults()));
                        if (isAdded()) {
                            itemAdapter.notifyItemRangeInserted(0, items.size());
                            showLoadingIndicator(false);
                            runLayoutAnimation(recyclerView);
                        }

                    }
                });
    }

    /**
     * OnFragmentInteractionListener
     * <p>
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int interaction, HashMap<String, String> data);
    }


    public void resetItemsObservable() {
        observableItemsList = BehaviorSubject.create();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = observableRetrofit.subscribe(new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("ItemList", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                observableItemsList.onError(e);
            }

            @Override
            public void onNext(Response models) {
                observableItemsList.onNext(models);
            }
        });
    }

    public Observable<Response> getItemsObservable() {
        if (observableItemsList == null) {
            resetItemsObservable();
        }
        return observableItemsList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void showLoadingIndicator(boolean show) {
        isLoading = show;
        if (isLoading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator()).rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator()).rotationBy(360).setDuration(500).setListener(this);
                }
            });
        } else {
            loadingIndicator.animate().cancel();
            loadingIndicator.setVisibility(View.GONE);
        }
    }
}