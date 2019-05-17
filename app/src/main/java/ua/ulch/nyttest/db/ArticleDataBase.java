package ua.ulch.nyttest.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Article.class}, version = 1 , exportSchema = false)
public abstract class ArticleDataBase extends RoomDatabase {
    public abstract ArticleRoomDao articleRoomDao();
}
