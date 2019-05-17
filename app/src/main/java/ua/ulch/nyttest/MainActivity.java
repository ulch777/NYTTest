package ua.ulch.nyttest;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ua.ulch.nyttest.fragments.TabsFragment;

import static butterknife.internal.Utils.arrayOf;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission()) {
            if (savedInstanceState == null)
                showFragment(new TabsFragment(), R.id.container_body, true);
        } else {
            requestPermission();
        }
    }

    public void showFragment(Fragment newFrag, int containerId, boolean addToBack) {
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (fragment instanceof TabsFragment) {
            finish();
        } else {
            showFragment(new TabsFragment(), R.id.container_body, true);
        }

    }

    private boolean checkPermission() {
        int result =
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ) {
            Toast.makeText(
                    MainActivity.this,
                    "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showFragment(new TabsFragment(), R.id.container_body, true);
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
                finish();
            }
        }
    }

}



