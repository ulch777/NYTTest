package ua.ulch.nyttest.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ArticleRoomDao {

    @Query("SELECT * FROM article")
    Flowable<List<Article>> getAll();

    @Query("SELECT * FROM article WHERE id = :id")
    Article getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);
}
