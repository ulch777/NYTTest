package ua.ulch.nyttest.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.ulch.nyttest.MainActivity;
import ua.ulch.nyttest.R;

public class TabsFragment extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private PagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        ButterKnife.bind(this, rootView);
        initRes();
        // Inflate the layout for this fragment
        return rootView;

    }

    protected void initRes() {
        pagerAdapter =
                new PagerAdapter(getChildFragmentManager(), getContext());
        viewpager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewpager);

    }


    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitles = new String[]{"The most emailed", "The most shared", "The most viewed",};
        private Context context;
        private EmailedFragment emailedFragment;
        private SharedFragment sharedFragment;
        private ViewedFragment viewedFragment;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            emailedFragment = new EmailedFragment();
            sharedFragment = new SharedFragment();
            viewedFragment = new ViewedFragment();
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return emailedFragment;
                case 1:
                    return sharedFragment;
                case 2:
                    return viewedFragment;
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }

    public ViewPager getViewPager() {
        return viewpager;
    }
}