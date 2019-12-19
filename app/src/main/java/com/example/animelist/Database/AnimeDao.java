package com.example.animelist.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface AnimeDao {

    @Query("SELECT * from animeentity")
    Flowable<List<AnimeEntity>> loadFavorites();

    @Insert
    public Completable addAnimeToFavorites(AnimeEntity bookEntity);

    @Query("DELETE FROM animeentity WHERE id = :id")
    public Completable deleteAnimeFromFavorites(String id);

    @Query("SELECT id from animeentity")
    Single<List<String>> getFavoriteIdList();
}
