package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.ulch.nyttest.R;
import ua.ulch.nyttest.model.Response;
import ua.ulch.nyttest.model.Results;
import ua.ulch.nyttest.networking.RetrofitSingleton;

public abstract class BaseFragment extends Fragment {

    protected Subscription subscription;
    @BindView(R.id.rv)
    RecyclerView recyclerView;



    /**
     * this method used to Set root layout of the fragment
     */
//    protected abstract int getLayoutRes();

    /**
     * this method used to initiate all the resources in the Fragment
     */
    protected abstract void initRes();
    protected abstract void getItemsList();
    protected abstract void restore(Bundle bundle);

    public abstract void shoWUnavailable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        if (savedInstanceState != null)
            restore(savedInstanceState);
        ButterKnife.bind(this, rootView);
        initRes();
        getItemsList();
        // Inflate the layout for this fragment
        return rootView;
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


    //    public void loadUrl(String url, MyWebView webView) {
//        if (URLUtil.isValidUrl(url)) {
//            SharedPreferences preferences = getActivity().getSharedPreferences("params", MODE_PRIVATE);
//            url += "?" + preferences.getString("parameters", "&source=organic&pid=1");
//            Log.e("url", url);
//            webView.setVisibility(View.VISIBLE);
//            webView.loadUrl(url);
//        } else {
//            ViewPager vp = ((TabsFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.container_body))).getViewPager();
//            BaseFragment page = (BaseFragment) getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + vp.getCurrentItem());
//            page.shoWUnavailable();
//
//        }
//    }

}