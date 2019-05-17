package ua.ulch.nyttest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.ulch.nyttest.R;

public class FavoriteDetailsFragment extends Fragment {
    private static final String HTML = "filename";
    private static final String KEY_HTML = "key_html";
    private String filename;
    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    public static FavoriteDetailsFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(HTML, url);
        FavoriteDetailsFragment fragment = new FavoriteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            filename = savedInstanceState.getString(KEY_HTML);
        } else {
            Bundle args = getArguments();
            if (args != null)
                filename = args.getString(HTML);
        }
        Log.e("FavoriteDetailsFragment", filename);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        initWebView(filename);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        return rootView;
    }

    private void initWebView(String html) {
        final String mimeType = "text/filename";
        final String encoding = "UTF-8";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        File file = new File(filename);
        webView.loadUrl("file:///storage/emulated/0/nyt_test/100000006497865.html");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_HTML, filename);
    }
}
