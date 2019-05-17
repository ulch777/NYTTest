package ua.ulch.nyttest.program;

import android.app.Application;
import android.arch.persistence.room.Room;

import ua.ulch.nyttest.db.ArticleDataBase;
import ua.ulch.nyttest.networking.NYTApiService;


public class App extends Application {
    private static App instance;
    private ArticleDataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        NYTApiService.init();
        instance = this;
        database = Room.databaseBuilder(this, ArticleDataBase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }
    public ArticleDataBase getDatabase() {
        return database;
    }
}
