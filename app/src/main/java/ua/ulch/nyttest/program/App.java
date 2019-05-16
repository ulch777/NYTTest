package ua.ulch.nyttest.program;

import android.app.Application;

import ua.ulch.nyttest.networking.RetrofitSingleton;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitSingleton.init();
    }
}
