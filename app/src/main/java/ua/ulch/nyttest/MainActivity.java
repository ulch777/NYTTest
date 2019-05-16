package ua.ulch.nyttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ua.ulch.nyttest.fragments.TabsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(new TabsFragment(), R.id.container_body, true);
    }

    protected void showFragment(Fragment newFrag, int containerId, boolean addToBack) {
        String backStateName = newFrag.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            Fragment f = manager.findFragmentByTag(backStateName);
            if (f != null) {
                newFrag = f;
            }
            FragmentTransaction ft = manager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
            ft.replace(containerId, newFrag, backStateName);

            if (addToBack) {
                ft.addToBackStack(backStateName);
            }
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
