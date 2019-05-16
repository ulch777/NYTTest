package ua.ulch.nyttest.program;

import android.app.Application;

import ua.ulch.nyttest.networking.NYTApiService;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NYTApiService.init();
    }
}
