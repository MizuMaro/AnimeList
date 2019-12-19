package com.example.animelist.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Requete de la base de donnée
 */
@Dao
public interface AnimeDao {

    /**
     * Recuperes tous les favoris
     * @return
     */
    @Query("SELECT * from animeentity")
    Flowable<List<AnimeEntity>> loadFavorites();

    /**
     * Ajoute l'animeEntity en favoris
     * @param bookEntity
     * @return
     */
    @Insert
    public Completable addAnimeToFavorites(AnimeEntity bookEntity);

    /**
     * Retire l'anime qui correspond à l'id
     * @param id
     * @return
     */
    @Query("DELETE FROM animeentity WHERE id = :id")
    public Completable deleteAnimeFromFavorites(String id);

    /**
     * Recupere la liste de tous les id favoris
     * @return
     */
    @Query("SELECT id from animeentity")
    Single<List<String>> getFavoriteIdList();
}
